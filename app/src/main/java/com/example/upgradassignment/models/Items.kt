package com.example.upgradassignment.models

import com.google.gson.annotations.Expose


class Items {


    @Expose
    var webSite: String? = null


    @Expose
    var profile: String? = null

    @Expose
    var displayName: String? = null

    @Expose
    var link: String? = null


    @Expose
    var location: String? = null


    @Expose
    var reputation: String? = null

    @Expose
    var age: String? = null


    @Expose
    var userId: String? = null

    @Expose
    var badges: Badges? = null

    @Expose
    var createDate: String? = null


    @Expose
    var lastAccessDate: String? = null


}