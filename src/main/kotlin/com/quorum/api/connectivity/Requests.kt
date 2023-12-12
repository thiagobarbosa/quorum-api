package com.quorum.api.connectivity

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

fun makePostRequest(url: String, ano: String, mes: String): String {
    val client = OkHttpClient()

    val formBody = FormBody.Builder()
        .add("ano", ano)
        .add("mes", mes)
        .build()

    val request = Request.Builder()
        .url(url)
        .post(formBody)
        .addHeader("Content-Type", "application/x-www-form-urlencoded")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        return response.body!!.string()
    }
}
