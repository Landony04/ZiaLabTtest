package com.zialab.data.repositories

import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import com.zialab.data.api.models.ResultSearchResponse
import com.zialab.data.mappers.toResultResponseUI
import com.zialab.data.utils.CoroutineTestRule
import com.zialab.data.utils.MockResponseFileReader
import com.zialab.data.utils.createInternalService
import com.zialab.domain.common.Result
import com.zialab.domain.entities.ResultSearchUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchUserRepositoryImplTest {
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val server = MockWebServer()
    private lateinit var sut: SearchUserRepositoryImpl
    private lateinit var mockedResponse: String
    private val gson = GsonBuilder().create()

    @Before
    fun init() {
        server.start(8000)
        val baseUrl = server.url("/").toString()
        sut = SearchUserRepositoryImpl(
            createInternalService(baseUrl),
            coroutinesTestRule.testDispatcherProvider
        )
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun retrieveResultSuccess_searchUser() {
        runBlocking {
            val url = "common/success-request-response-200.json"
            val successHttpCode = 200
            val expectedItemListSize = 2
            mockedResponse =
                MockResponseFileReader(url).content
            server.enqueue(
                MockResponse().setResponseCode(successHttpCode).setBody(mockedResponse)
            )
            val expectedModel =
                gson.fromJson(mockedResponse, ResultSearchResponse::class.java)
                    .toResultResponseUI()

            val actualFlow = sut.searchUser("lando", 50)
            val stateList = mutableListOf<Result<ResultSearchUI>>()

            actualFlow.collect {
                stateList.add(it)
            }

            val unWrapperValue = stateList.last() as Result.Success

            assertThat(stateList.first()).isInstanceOf(Result.Loading::class.java)
            assertThat(stateList.size).isEqualTo(expectedItemListSize)
            assertThat(unWrapperValue.data).isEqualTo(expectedModel)
        }
    }
}