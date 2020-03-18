package com.outcomehealth.data.api

import com.outcomehealth.lib.VideoOH
import retrofit2.http.GET

interface VideoApi {

    @GET("code-challenge-manifest.json")
    suspend fun loadVideoManifest(): List<VideoOH>
}