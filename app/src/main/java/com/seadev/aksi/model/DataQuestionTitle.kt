package com.seadev.aksi.model

import com.seadev.aksi.R

class DataQuestionTitle {
    companion object {
        var questionTitleQ1: MutableList<Int> = mutableListOf()
        var questionTitleQ2: MutableList<Int> = mutableListOf()
        var questionTitleQ3: MutableList<Int> = mutableListOf()

        fun getQuestion1(): MutableList<Int> {
            questionTitleQ1.add(R.string.question_title_1)
            questionTitleQ1.add(R.string.question_title_2)
            questionTitleQ1.add(R.string.question_title_3)
            questionTitleQ1.add(R.string.question_title_4)
            questionTitleQ1.add(R.string.question_title_5)
            questionTitleQ1.add(R.string.question_title_6)
            questionTitleQ1.add(R.string.question_title_7)
            questionTitleQ1.add(R.string.question_title_8)
            questionTitleQ1.add(R.string.question_title_9)
            questionTitleQ1.add(R.string.question_title_10)
            return questionTitleQ1
        }

        fun getQuestion2(): MutableList<Int> {
            questionTitleQ2.add(R.string.question_title_11)
            questionTitleQ2.add(R.string.question_title_12)
            questionTitleQ2.add(R.string.question_title_13)
            questionTitleQ2.add(R.string.question_title_14)
            questionTitleQ2.add(R.string.question_title_15)
            questionTitleQ2.add(R.string.question_title_16)
            return questionTitleQ2
        }

        fun getQuestion3(): MutableList<Int> {
            questionTitleQ3.add(R.string.question_title_17)
            questionTitleQ3.add(R.string.question_title_18)
            questionTitleQ3.add(R.string.question_title_19)
            questionTitleQ3.add(R.string.question_title_20)
            questionTitleQ3.add(R.string.question_title_21)
            return questionTitleQ3
        }
    }
}