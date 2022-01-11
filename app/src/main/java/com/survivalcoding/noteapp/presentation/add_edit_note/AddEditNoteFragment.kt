package com.survivalcoding.noteapp.presentation.add_edit_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.databinding.FragmentAddEditNoteBinding

class AddEditNoteFragment : Fragment() {
    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AddEditNoteViewModel> {
        AddEditNoteViewModelFactory(
            notesRepository = (requireActivity().application as App).notesRepository,
            owner = this,
            defaultArgs = arguments
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //ToDo: 수정 및 추가 기능 구현
        val saveButton = binding.saveButton
        saveButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}