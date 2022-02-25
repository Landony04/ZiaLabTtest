package com.zialab.data.api

import com.zialab.data.api.models.ResultSearchResponse
import com.zialab.data.api.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ZiaLabApiService {

    /**
     * ZIALAB SERVICES
     * fun getGitHubUsers has the autocomplete service path
     * @param user
     * @param page
     * @param perPage
     */
    @GET(ApiRoutes.GITHUB_SEARCH_USER)
    suspend fun getGitHubUsers(
        @Query("q") user: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Header("Authorization") token: String
    ): Response<ResultSearchResponse>

    /**
     * ZIALAB SERVICES
     * fun getUsersWithNumberRepos  service path
     * @param user
     */
    @GET(ApiRoutes.GITHUB_REPOS_BY_USER)
    suspend fun getNumberReposByUser(
        @Path("user") user: String,
        @Header("Authorization") token: String
    ): Response<UserResponse>
}