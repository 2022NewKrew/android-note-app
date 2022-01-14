package com.survivalcoding.noteapp.presentation

import com.survivalcoding.noteapp.presentation.notes.NotesFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.survivalcoding.noteapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<NotesFragment>(R.id.main_fragment_container_view)
                setReorderingAllowed(true)
            }
        }
    }
}