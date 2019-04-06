package com.example.upgradassignment.models

import com.google.gson.annotations.Expose


class QuestionItems {
    @Expose
    var tags: List<String> = ArrayList()

    @Expose
    var is_answered: Boolean = false

    @Expose
    var view_count: String? = null

    @Expose
    var answer_count: String? = null

    @Expose
    var score: String? = null

    @Expose
    var creation_date: String? = null

    @Expose
    var link: String? = null

    @Expose
    var title: String? = null


}