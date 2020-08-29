package com.tutorialsbuzz.notification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_notify.*

class NotifyActivity : AppCompatActivity() {

    companion object {
        const val notify_title: String = "notify_title"
        const val notify_content: String = "notify_content"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)
        updateUI(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        updateUI(intent)
    }

    private fun updateUI(intent: Intent?): Unit {
        val title = (intent?.extras?.get(notify_title)) as String?
        val content = intent?.extras?.get(notify_content) as String?
        notifyText.text = title + "\n" + content
    }


}