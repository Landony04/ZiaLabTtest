package com.zialab.searchgithub.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

    private var isProgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchUsersEditText.addTextChangedListener(this)

        setUpObserver()
    }

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
    }

    private fun setupUsersAdapter(allValues: ArrayList<UserUI>) {
        if (allValues.isNotEmpty()) {
            binding.withoutResults.visibility = View.GONE
            val layoutManager = LinearLayoutManager(this)
            binding.usersList.layoutManager = layoutManager

            val adapter = SearchUserAdapter(
                this@MainActivity,
                allValues
            )

            binding.usersList.adapter = adapter
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
                50
            )
            runBlocking {
                delay(100L)
                viewModel.searchUsers(searchUserRequestUI)
            }
        } else {
            binding.usersList.visibility = View.GONE
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }
}