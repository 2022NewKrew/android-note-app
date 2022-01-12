package com.survivalcoding.noteapp.presentation.add_edit_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.databinding.FragmentAddEditNoteBinding
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.usecase.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase

class AddEditNoteFragment : Fragment() {

    private val viewModel: AddEditNoteViewModel by viewModels {
        val noteRepository = (requireActivity().application as App).noteRepository
        AddEditNoteViewModelFactory(
            InsertNoteUseCase(noteRepository),
            DeleteNoteUseCase(noteRepository)
        )
    }

    private val binding: FragmentAddEditNoteBinding by lazy {
        FragmentAddEditNoteBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(note: Note?) = AddEditNoteFragment().apply {
            arguments = Bundle().apply {
//                putParcelable()
            }
        }
    }
}