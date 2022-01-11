package com.survivalcoding.noteapp.notes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.MainViewModel
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.presentation.notes.NotesViewModel
import com.survivalcoding.noteapp.presentation.notes.NotesViewModelFacotry

class MainFragment : Fragment() {

    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this, NotesViewModelFacotry(
                (requireActivity().application as App).repository
            )
        ).get(NotesViewModel::class.java)

    }

}