package com.survivalcoding.noteapp.presentation.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNoteBinding

class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}