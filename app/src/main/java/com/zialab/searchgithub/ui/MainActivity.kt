package com.zialab.searchgithub.ui

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.zialab.domain.common.Result
import com.zialab.domain.entities.SearchUserRequestUI
import com.zialab.domain.entities.UserUI
import com.zialab.searchgithub.databinding.ActivityMainBinding
import com.zialab.searchgithub.ui.adapter.SearchUserAdapter
import com.zialab.searchgithub.viewModels.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TextWatcher {

    private val viewModel: SearchUserViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchUserAdapter

    private var isProgress = false

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchUsersEditText.addTextChangedListener(this)

        setUpObserver()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setUpObserver() {
        viewModel.searchUsersInformation.observe(this) {
            it?.let {
                when (it) {
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.usersList.visibility = View.VISIBLE
                        setupUsersAdapter(it.data.usersItems)
                        isProgress = false
                    }

                    is Result.Loading -> {
                        isProgress = true
                        binding.usersList.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        binding.usersList.visibility = View.GONE
                        binding.withoutResults.visibility = View.VISIBLE
                        isProgress = false
                    }
                }
            }
        }

        viewModel.reposByUserInformation.observe(this) {
            it?.let {
                when (it) {
                    is Result.Success -> {
                        adapter.updateItem(it.data)
                    }
                    is Result.Failure -> {
                        Log.d("MainActivity", "FAILURE")
                    }
                    is Result.Loading -> {
                        Log.d("MainActivity", "LOADING")
                    }
                }
            }
        }
    }

    private fun setupUsersAdapter(allValues: ArrayList<UserUI>) {
        if (allValues.isNotEmpty()) {

            binding.withoutResults.visibility = View.GONE
            binding.usersList.setHasFixedSize(true)

            adapter = SearchUserAdapter(
                this@MainActivity,
                allValues
            ) {
                viewModel.getReposByUser(it)
            }

            binding.usersList.adapter = adapter
            adapter.submitList(allValues)
        } else {
            binding.usersList.visibility = View.GONE
            binding.withoutResults.visibility = View.VISIBLE
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (s.toString().length > 3 && !isProgress) {
            val searchUserRequestUI = SearchUserRequestUI(
                s.toString(),
                PER_PAGE
            )
            runBlocking {
                delay(DELAY_DEFAULT)
                viewModel.searchUsers(searchUserRequestUI)
            }
        } else {
            binding.usersList.visibility = View.GONE
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    companion object {
        const val DELAY_DEFAULT = 100L
        const val PER_PAGE = 50
    }
}