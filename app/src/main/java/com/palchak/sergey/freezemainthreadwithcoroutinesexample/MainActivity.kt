package com.palchak.sergey.freezemainthreadwithcoroutinesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main()

        click_me_button.setOnClickListener {
            counter_text.text = counter++.toString()
        }
    }

    private fun main() {
        CoroutineScope(Main).launch {
            // Due to very large number of coroutines Main thread will be frozen
            // In real app it is prohibited to use Main thread for network requests!!!!!!
            for (i in 1..100_000) {
                launch {
                    doNetworkRequest(i)
                }
            }
        }
    }

    private suspend fun doNetworkRequest(requestNumber: Int) {
        println("Current thread: ${Thread.currentThread().name}")
        println("Starting network request...$requestNumber")
        delay(3000)
        println("Finished network request! $requestNumber")
    }
}