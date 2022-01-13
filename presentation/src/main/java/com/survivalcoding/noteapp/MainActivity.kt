package com.survivalcoding.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.survivalcoding.noteapp.notes.MainFragment

class MainActivity : AppCompatActivity() {

    companion object {
        val FRAGMENT_KEY = "CHANNEL1"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            replace<MainFragment>(R.id.fragment_container_view)
        }

    }
}