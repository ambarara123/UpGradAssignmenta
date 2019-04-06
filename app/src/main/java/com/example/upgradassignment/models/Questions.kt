package com.example.upgradassignment.models

import com.google.gson.annotations.Expose


class Questions {
    @Expose
    var items: List<QuestionItems> = ArrayList<QuestionItems>()

}