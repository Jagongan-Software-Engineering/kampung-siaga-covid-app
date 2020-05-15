package com.seadev.aksi.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.quickbirdstudios.surveykit.*
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.steps.CompletionStep
import com.quickbirdstudios.surveykit.steps.InstructionStep
import com.quickbirdstudios.surveykit.steps.QuestionStep
import com.quickbirdstudios.surveykit.steps.Step
import com.quickbirdstudios.surveykit.survey.SurveyView
import com.seadev.aksi.BuildConfig
import com.seadev.aksi.R
import com.seadev.aksi.model.Asesmen
import com.seadev.aksi.model.DataQuestionTitle
import com.seadev.aksi.model.Users
import com.seadev.aksi.room.AsesmenContract.db
import com.seadev.aksi.room.AssesmenDatabase
import com.seadev.aksi.util.DateFormater
import com.seadev.aksi.util.ReportHistoryFormater
import kotlinx.android.synthetic.main.activity_survey.*
import java.text.SimpleDateFormat
import java.util.*

class SurveyActivity : AppCompatActivity() {

    private val TAG = "SurveyActivity"
    protected lateinit var survey: SurveyView
    private lateinit var container: ViewGroup
    private lateinit var mStep: MutableList<Step>
    private lateinit var mAnswer: MutableList<Int>
    private lateinit var refAssesment: DatabaseReference
    private lateinit var refUsers: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var mValueEventListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        supportActionBar?.title = "Kuesioner Penilaian Diri"
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        auth = FirebaseAuth.getInstance()
        refAssesment = FirebaseDatabase.getInstance().reference.child("asesmen")
        refUsers = FirebaseDatabase.getInstance().reference.child("users")
        initView()
    }

    private fun initView() {
        db = Room.databaseBuilder(Objects.requireNonNull(this),
                AssesmenDatabase::class.java, "db_asesmen").allowMainThreadQueries().build()
        val asesmenList = db.asesmenDao().dataAsesmen
        if (!asesmenList.isEmpty()) {
            val (date1) = asesmenList[asesmenList.size - 1]
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val nowDate = formatter.format(Date())
            val isToday = if (nowDate == date1) 1 else 0
            if (isToday == 1) {
                layoutScroll.visibility = View.VISIBLE
                layoutDoneSurvey.visibility = View.VISIBLE
                layoutGuideSurvey.visibility = View.GONE
                btnSurveyBack.setOnClickListener { finish() }
                Glide.with(this)
                        .load(BuildConfig.BASE_URL_LOKASI + getString(R.string.res_icon_assesment))
                        .into(ivImgDoneSurvey)
            } else {
                Glide.with(this)
                        .load(BuildConfig.BASE_URL_LOKASI + "res%2F068-settings.png?alt=media&token=700c2e17-e8d0-4ef5-b9a6-9a8804e1e294")
                        .into(ivImgGuideSurvey)
                btnGuideOK.setOnClickListener {
                    layoutScroll.visibility = View.GONE
                    layoutDoneSurvey.visibility = View.GONE
                    layoutGuideSurvey.visibility = View.GONE
                    survey_view.visibility = View.VISIBLE
                    survey = findViewById(R.id.survey_view)
                    survey.onCancelPendingInputEvents()
                    container = findViewById(R.id.surveyContainer)
                    initialQuestion()
                    setupSurvey(survey)

                }

            }
        } else {
            Glide.with(this)
                    .load(BuildConfig.BASE_URL_LOKASI + "res%2F068-settings.png?alt=media&token=700c2e17-e8d0-4ef5-b9a6-9a8804e1e294")
                    .into(ivImgGuideSurvey)
            btnGuideOK.setOnClickListener {
                layoutScroll.visibility = View.GONE
                layoutDoneSurvey.visibility = View.GONE
                layoutGuideSurvey.visibility = View.GONE
                survey_view.visibility = View.VISIBLE
                survey = findViewById(R.id.survey_view)
                survey.onCancelPendingInputEvents()
                container = findViewById(R.id.surveyContainer)
                initialQuestion()
                setupSurvey(survey)

            }
        }
    }

    override fun onBackPressed() {
        onBack()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBack()
        return super.onOptionsItemSelected(item)
    }

    private fun onBack() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Jika Anda keluar, data yang Anda isi akan di abaikan.")
                .setPositiveButton("Ya") { dialog, which ->
                    survey.onSurveyFinish
                    survey.removeAllViews()
                    container.removeAllViews()
                    finish()
                }
                .setNegativeButton("Batal") { dialog, which -> }
        builder.setTitle("Apakah Anda yakin ingin keluar dari penilaian diri sekarang?")
        builder.create().show()
    }

    private fun setupSurvey(surveyView: SurveyView) {
        val steps = mStep
        val task = NavigableOrderedTask(steps = steps)

        surveyView.onSurveyFinish = { taskResult: TaskResult, reason: FinishReason ->
            mAnswer = mutableListOf()
            if (reason == FinishReason.Completed) {
                taskResult.results.forEach { stepResult ->
                    stepResult.results.forEachIndexed { index, questionResult ->
                        if (questionResult.stringIdentifier.isNotEmpty()) {
                            val ans = if (questionResult.stringIdentifier == "Ya") 1 else 0
                            mAnswer.add(ans)
                            Log.d("ASDF", "answer ${questionResult.stringIdentifier}")
                            Log.d("ASDF", "answer ${questionResult.endDate}")
                        }
                    }
                    container.removeAllViews()
                }
                var users: Users? = null
                if (mValueEventListener == null) {
                    mValueEventListener = object : ValueEventListener {
                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.d(TAG, "loadUser:onCancelled ${databaseError.toException()}")
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (postSnapshot in dataSnapshot.children) {
                                users = postSnapshot.getValue(Users::class.java)
                            }
                            validation(users)
                        }
                    }
                    val firebaseUser = auth.currentUser
                    refUsers.orderByChild("phone").equalTo(firebaseUser?.phoneNumber)
                            .addValueEventListener(mValueEventListener as ValueEventListener)
                }

            }
        }

        val configuration = SurveyTheme(
                themeColorDark = ContextCompat.getColor(this, android.R.color.darker_gray),
                themeColor = ContextCompat.getColor(this, R.color.colorPrimary),
                textColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        )

        surveyView.start(task, configuration)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            true
        } else false
    }

    private fun initialQuestion() {
        val questionTitle1 = DataQuestionTitle.getQuestion1()
        val questionTitle2 = DataQuestionTitle.getQuestion2()
        val questionTitle3 = DataQuestionTitle.getQuestion3()

        mStep = mutableListOf()
        mStep.add(InstructionStep(
                title = R.string.intro_title,
                text = R.string.intro_text,
                buttonText = R.string.intro_start))

        questionTitle1.forEach {
            mStep.add(QuestionStep(
                    title = it,
                    text = R.string.boolean_example_text,
                    nextButton = R.string.intro_start,
                    answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                            textChoices = listOf(
                                    TextChoice(R.string.yes),
                                    TextChoice(R.string.no)
                            )
                    )
            ))
        }

        mStep.add(InstructionStep(
                title = R.string.intro_title_2,
                text = R.string.intro_text_2,
                buttonText = R.string.intro_start))

        questionTitle2.forEach {
            mStep.add(QuestionStep(
                    title = it,
                    text = R.string.boolean_example_text,
                    nextButton = R.string.intro_start,
                    answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                            textChoices = listOf(
                                    TextChoice(R.string.yes),
                                    TextChoice(R.string.no)
                            )
                    )
            ))
        }

        mStep.add(InstructionStep(
                title = R.string.intro_title_3,
                text = R.string.intro_text_3,
                buttonText = R.string.intro_start))

        questionTitle3.forEach {
            mStep.add(QuestionStep(
                    title = it,
                    text = R.string.boolean_example_text,
                    nextButton = R.string.intro_start,
                    answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                            textChoices = listOf(
                                    TextChoice(R.string.yes),
                                    TextChoice(R.string.no)
                            )
                    )
            ))
        }

        mStep.add(CompletionStep(
                title = R.string.finish_titile,
                text = R.string.finish_text,
                buttonText = R.string.finish_btn
        ))
    }

    private fun validation(users: Users?) {
        var datePath = ""
        var idDesa = users?.idDesa
        var idUser = users?.idUser
        var mData = "("
        var counter = 0
        var mRisiko = ""
        var date = ""
        var rtrw = users?.rtrw
        val dataValid = mutableListOf(1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1)

        val current = Date()
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val formatter2 = SimpleDateFormat("EEEE dd MMMM yyyy HH:mm")
        datePath = formatter.format(current)
        val formatted2 = formatter2.format(current)
        val listDate = formatted2.split(" ")
        date = "${DateFormater.getHari(listDate[0], 0)}, ${listDate[1]} ${DateFormater.getBulan(listDate[2])} ${listDate[3]}, ${listDate[4]}"

        mAnswer.forEachIndexed { index, i ->
            if (i == dataValid[index]) counter++
            Log.d("QuestionAnswer", "answer-$index: $i")
            mData += if (index != mAnswer.size - 1) "$i, " else "$i)"
        }
        when (counter) {
            in 0..7 -> {
                mRisiko = "rendah"
                Log.d("QuestionAnswer", "Risiko Rendah")
            }
            in 8..14 -> {
                mRisiko = "sedang"
                Log.d("QuestionAnswer", "Risiko Sedang")
            }
            in 15..21 -> {
                mRisiko = "tinggi"
                Log.d("QuestionAnswer", "Risiko Tinggi")
            }
            else -> Log.d("QuestionAnswer", "Data not Valid")
        }

        Log.d("QuestionAnswer", "DatePath: $datePath")
        Log.d("QuestionAnswer", "IdDesa: $idDesa")
        Log.d("QuestionAnswer", "IdUser: $idUser")
        Log.d("QuestionAnswer", "DataSet: $mData")
        Log.d("QuestionAnswer", "Counter: $counter")
        Log.d("QuestionAnswer", "Risiko: $mRisiko")
        Log.d("QuestionAnswer", "Date: $date")
        Log.d("QuestionAnswer", "Rt/Rw: $rtrw")
        val dataAsesmen = Asesmen(date, idUser, mData, counter, mRisiko, rtrw)
        refAssesment.child(idDesa!!).child(datePath).child(idUser!!)
                .setValue(dataAsesmen).addOnSuccessListener {
                    Toast.makeText(this, "Data berhasil di simpan", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Data gagal di simpan", Toast.LENGTH_SHORT).show()
                }
        dataAsesmen.date = datePath
        db.asesmenDao().InsertDataAsesmen(dataAsesmen)
        val intent = Intent(this, DetailReportActivity::class.java)
        intent.putExtra(DetailReportActivity.DATA_DETAIL_EXTRA, ReportHistoryFormater
                .getTitleReport(dataAsesmen.risiko!!).toLowerCase() + " result")
        startActivity(intent)
        finish()
    }
}
