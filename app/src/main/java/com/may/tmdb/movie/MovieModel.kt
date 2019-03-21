package com.may.tmdb.movie

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import com.google.gson.annotations.SerializedName
import com.may.tmdb.configuration.ConfigurationImageModel

data class MovieModel(
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("genre_ids") val genresId: IntArray,
    var largePosterPath: String? = "",
    var thumbnailPosterPath: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.createIntArray(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    fun withConfiguration(configuration: ConfigurationImageModel): MovieModel {
        if (posterPath != null) {
            // everything enclosed of 'wXXX'
            thumbnailPosterPath =
                configuration.baseUrl + configuration.posterSizes.first { it.contains("^(w\\d{3})$".toRegex()) } + posterPath
            largePosterPath =
                configuration.baseUrl + configuration.posterSizes.last { it.contains("^(w\\d{3})$".toRegex()) } + posterPath
        }
        return this
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(overview)
        parcel.writeIntArray(genresId)
        parcel.writeString(largePosterPath)
        parcel.writeString(thumbnailPosterPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieModel> {
        override fun createFromParcel(parcel: Parcel): MovieModel {
            return MovieModel(parcel)
        }

        override fun newArray(size: Int): Array<MovieModel?> {
            return arrayOfNulls(size)
        }
    }
}