package com.may.tmdb.movie

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import com.google.gson.annotations.SerializedName
import com.may.tmdb.configuration.ConfigurationImageModel

data class MovieModel(
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("genre_ids") val genreId: IntArray,
    @SerializedName("id") val id: Int,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("title") val title: String,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    var largePosterPath: String? = "",
    var thumbnailPosterPath: String? = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.createIntArray(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    fun withConfiguration(configuration: ConfigurationImageModel): MovieModel {
        if (posterPath != null && !posterPath.contains(configuration.baseUrl)) {
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
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeString(overview)
        parcel.writeString(releaseDate)
        parcel.writeIntArray(genreId)
        parcel.writeInt(id)
        parcel.writeString(originalTitle)
        parcel.writeString(originalLanguage)
        parcel.writeString(title)
        parcel.writeString(backdropPath)
        parcel.writeDouble(popularity)
        parcel.writeInt(voteCount)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeDouble(voteAverage)
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