package com.survivalcoding.note_app.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.survivalcoding.note_app.R
import com.survivalcoding.note_app.data.data_source.NoteDatabase
import com.survivalcoding.note_app.data.repository.NoteRepositoryImpl
import com.survivalcoding.note_app.databinding.FragmentNotesBinding
import com.survivalcoding.note_app.presentation.add_edit_note.AddEditNoteFragment
import com.survivalcoding.note_app.presentation.notes.adapter.NoteListAdapter
import java.lang.IllegalArgumentException

class NotesFragment : Fragment(R.layout.fragment_notes) {
    private var _binding: FragmentNotesBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<NotesViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
                        val db = Room.databaseBuilder(
                            requireContext(),
                            NoteDatabase::class.java, "notes-db"
                        ).build()

                        return NotesViewModel(NoteRepositoryImpl(db.noteDao)) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel Class")
                }
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notesAdapter = NoteListAdapter()

        binding.notesList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notesAdapter
        }

        binding.addButton.setOnClickListener {
            parentFragmentManager.commit {
                replace<AddEditNoteFragment>(R.id.fragment_container)
                setReorderingAllowed(true)
                addToBackStack("addEditNote")
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            notesAdapter.submitList(state.notes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(viewModel)
        _binding = null
    }
}