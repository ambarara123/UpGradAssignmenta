package com.example.upgradassignment.models

import android.os.Parcel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TagsModel : Serializable {

    @SerializedName("items")
    @Expose
    var items: List<com.example.upgradassignment.models.Item>? = null
    @SerializedName("has_more")
    @Expose
    var hasMore: Boolean? = null
    @SerializedName("quota_max")
    @Expose
    var quotaMax: Long? = null
    @SerializedName("quota_remaining")
    @Expose
    var quotaRemaining: Long? = null

    protected constructor(`in`: Parcel) {
        `in`.readList(this.items, com.example.upgradassignment.models.Item::class.java.classLoader)
        this.hasMore = `in`.readValue(Boolean::class.java.classLoader) as Boolean
        this.quotaMax = `in`.readValue(Long::class.java.classLoader) as Long
        this.quotaRemaining = `in`.readValue(Long::class.java.classLoader) as Long
    }


    constructor(
        items: List<com.example.upgradassignment.models.Item>,
        hasMore: Boolean?,
        quotaMax: Long?,
        quotaRemaining: Long?
    ) : super() {
        this.items = items
        this.hasMore = hasMore
        this.quotaMax = quotaMax
        this.quotaRemaining = quotaRemaining
    }


}