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
import com.survivalcoding.note_app.App
import com.survivalcoding.note_app.R
import com.survivalcoding.note_app.databinding.FragmentNotesBinding
import com.survivalcoding.note_app.presentation.add_edit_note.AddEditNoteFragment
import com.survivalcoding.note_app.presentation.notes.adapter.NoteListAdapter

class NotesFragment : Fragment(R.layout.fragment_notes) {
    private var _binding: FragmentNotesBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<NotesViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
                        val repository = (requireActivity().application as App).repository

                        return NotesViewModel(repository) as T
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