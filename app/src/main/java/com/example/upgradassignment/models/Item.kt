package com.example.upgradassignment.models

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Item : Serializable, Parcelable {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeValue(hasSynonyms)
        dest.writeValue(isModeratorOnly)
        dest.writeValue(isRequired)
        dest.writeValue(count)
        dest.writeValue(name)
    }

    @SerializedName("has_synonyms")
    @Expose
    var hasSynonyms: Boolean? = null
    @SerializedName("is_moderator_only")
    @Expose
    var isModeratorOnly: Boolean? = null
    @SerializedName("is_required")
    @Expose
    var isRequired: Boolean? = null
    @SerializedName("count")
    @Expose
    var count: Long? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

    protected constructor(`in`: Parcel) {
        this.hasSynonyms = `in`.readValue(Boolean::class.java.classLoader) as Boolean
        this.isModeratorOnly = `in`.readValue(Boolean::class.java.classLoader) as Boolean
        this.isRequired = `in`.readValue(Boolean::class.java.classLoader) as Boolean
        this.count = `in`.readValue(Long::class.java.classLoader) as Long
        this.name = `in`.readValue(String::class.java.classLoader) as String
    }


    constructor(
        hasSynonyms: Boolean?,
        isModeratorOnly: Boolean?,
        isRequired: Boolean?,
        count: Long?,
        name: String
    ) : super() {
        this.hasSynonyms = hasSynonyms
        this.isModeratorOnly = isModeratorOnly
        this.isRequired = isRequired
        this.count = count
        this.name = name
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }


}