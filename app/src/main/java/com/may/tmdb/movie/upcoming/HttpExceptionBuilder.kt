package com.may.tmdb.movie.upcoming

import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class HttpExceptionBuilder {
    private var mCode = 400
    fun code(code: Int): HttpExceptionBuilder {
        mCode = code
        return this
    }

    fun build(): HttpException {
        val response = Response.error<String>(mCode, ResponseBody.create( MediaType.parse("application/json"),"{}"))
        return HttpException(response)
    }

}
