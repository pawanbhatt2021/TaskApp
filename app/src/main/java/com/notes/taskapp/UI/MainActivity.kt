package com.notes.taskapp.UI

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.notes.taskapp.UI.adapter.GameAdapter
import com.notes.taskapp.databinding.ActivityMainBinding
import com.notes.taskapp.models.ApiData
import com.notes.taskapp.retrofit.utils.ApiResponse
import com.notes.taskapp.viewmodel.ViewModelClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private lateinit var gameAdapter: GameAdapter
    private lateinit var apiData: ApiData
    private val vieModel: ViewModelClass by viewModels()
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getData() {
        lifecycleScope.launch(Dispatchers.IO) {
            vieModel.getGameData().collect {

                when (it) {
                    is ApiResponse.Success -> {
                        withContext(Dispatchers.Main) {
                            binding.loading.visibility = View.GONE
                            binding.gameRecycleView.visibility = View.VISIBLE
                            apiData = it.data!!
                            val gson = Gson()
                            val json = gson.toJson(apiData)
                            val pref = getSharedPreferences("gameData", MODE_PRIVATE)
                            pref.edit().putString("gameData", json).apply()
                            gameAdapter = GameAdapter(apiData)
                            binding.gameRecycleView.layoutManager = LinearLayoutManager(
                                this@MainActivity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            binding.gameRecycleView.adapter = gameAdapter
                        }
                    }

                    is ApiResponse.Failure -> {
                        withContext(Dispatchers.Main) {
                            val sharedPreferences =
                                getSharedPreferences("gameData", MODE_PRIVATE)
                            val json = sharedPreferences.getString(
                                "gameData",
                                null
                            ) // Retrieve the JSON string
                            Log.d("Failure",json.toString())
                            if (json != null) {
                                binding.loading.visibility = View.GONE
                                binding.gameRecycleView.visibility = View.VISIBLE
                                val gson = Gson()
                                val data = gson.fromJson(json, ApiData::class.java)
                                apiData = data
                                gameAdapter = GameAdapter(apiData)
                                binding.gameRecycleView.layoutManager = LinearLayoutManager(
                                    this@MainActivity,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                binding.gameRecycleView.adapter = gameAdapter
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Failed to load data!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    is ApiResponse.Loading -> {
                        runOnUiThread {
                            Toast.makeText(
                                this@MainActivity,
                                "Data is loading",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }
            }
        }
    }
}