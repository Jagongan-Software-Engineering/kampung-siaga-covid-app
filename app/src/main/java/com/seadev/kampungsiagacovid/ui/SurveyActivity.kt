package com.seadev.kampungsiagacovid.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.quickbirdstudios.surveykit.*
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.steps.CompletionStep
import com.quickbirdstudios.surveykit.steps.InstructionStep
import com.quickbirdstudios.surveykit.steps.QuestionStep
import com.quickbirdstudios.surveykit.steps.Step
import com.quickbirdstudios.surveykit.survey.SurveyView
import com.seadev.kampungsiagacovid.R
import com.seadev.kampungsiagacovid.model.DataQuestionTitle

class SurveyActivity : AppCompatActivity() {

    protected lateinit var survey: SurveyView
    private lateinit var container: ViewGroup
    private lateinit var mStep: MutableList<Step>
    private lateinit var mAnswer: MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        supportActionBar?.title = "Koesioner Penilaian Diri"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        survey = findViewById(R.id.survey_view)
        container = findViewById(R.id.surveyContainer)
        initialQuestion()
        setupSurvey(survey)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
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
                        }
                    }
                    container.removeAllViews()
                }
                validation()
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
            survey.backPressed()
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
                title = R.string.intro_title,
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
                title = R.string.intro_title,
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

    private fun validation() {
        val dataValid = mutableListOf(1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1)
        var counter = 0
        mAnswer.forEachIndexed { index, i ->
            if (i == dataValid[index]) counter++
            Log.d("QuestionAnswer", "answer-$index: $i")
        }
        Log.d("QuestionAnswer", "Counter: $counter")
        when (counter) {
            in 0..7 -> Log.d("QuestionAnswer", "Risiko Rendah")
            in 8..14 -> Log.d("QuestionAnswer", "Risiko Sedang")
            in 15..21 -> Log.d("QuestionAnswer", "Risiko Tinggi")
            else -> Log.d("QuestionAnswer", "Data not Valid")
        }
    }
}
