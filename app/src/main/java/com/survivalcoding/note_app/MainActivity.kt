package com.survivalcoding.note_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.survivalcoding.note_app.databinding.ActivityMainBinding
import com.survivalcoding.note_app.presentation.notes.NotesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            replace<NotesFragment>(R.id.fragment_container)
            setReorderingAllowed(true)
        }
    }
}