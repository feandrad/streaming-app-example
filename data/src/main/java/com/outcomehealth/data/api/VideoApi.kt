package com.outcomehealth.data.api

import com.outcomehealth.data.api.response.VideoResponse
import retrofit2.http.GET

interface VideoApi {

    @GET("code-challenge-manifest.json")
    suspend fun loadVideoManifest(): List<VideoResponse>
}