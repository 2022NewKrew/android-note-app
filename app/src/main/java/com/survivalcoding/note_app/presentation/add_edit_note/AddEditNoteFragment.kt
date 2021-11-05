package com.survivalcoding.note_app.presentation.add_edit_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.room.Room
import androidx.savedstate.SavedStateRegistryOwner
import com.survivalcoding.note_app.R
import com.survivalcoding.note_app.data.data_source.NoteDatabase
import com.survivalcoding.note_app.data.repository.NoteRepositoryImpl
import com.survivalcoding.note_app.databinding.FragmentAddEditNoteBinding
import com.survivalcoding.note_app.databinding.FragmentNotesBinding
import com.survivalcoding.note_app.presentation.notes.NotesViewModel
import java.lang.IllegalArgumentException

class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {
    private var _binding: FragmentAddEditNoteBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<AddEditNoteViewModel>(
        factoryProducer = {
            MyViewModelFactory(requireActivity())
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            viewModel.onEvent(
                AddEditNoteEvent.SaveNote(
                    title = binding.titleEditText.text.toString(),
                    content = binding.contentEditText.text.toString(),
                )
            )
        }

        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                AddEditNoteViewModel.UiEvent.SaveNote -> {
                    parentFragmentManager.popBackStack()
                }
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> TODO()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class MyViewModelFactory(owner: SavedStateRegistryOwner, defaultArgs: Bundle? = null) :
        AbstractSavedStateViewModelFactory(
            owner, defaultArgs
        ) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            if (modelClass.isAssignableFrom(AddEditNoteViewModel::class.java)) {
                val db = Room.databaseBuilder(
                    requireContext(),
                    NoteDatabase::class.java, "notes-db"
                ).build()

                return AddEditNoteViewModel(NoteRepositoryImpl(db.noteDao), handle) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}