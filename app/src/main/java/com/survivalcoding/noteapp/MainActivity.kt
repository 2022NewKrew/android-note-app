package com.survivalcoding.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.survivalcoding.noteapp.databinding.ActivityMainBinding
import com.survivalcoding.noteapp.presentation.notes.NotesFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, NotesFragment.newInstance())
        }
    }
}