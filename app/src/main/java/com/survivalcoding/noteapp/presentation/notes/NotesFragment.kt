package com.survivalcoding.noteapp.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNotesBinding
import com.survivalcoding.noteapp.domain.usecase.GetNotesByOrderUseCase
import com.survivalcoding.noteapp.presentation.add_edit_note.AddEditNoteFragment

class NotesFragment : Fragment() {

    private val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory(
            GetNotesByOrderUseCase((requireActivity().application as App).noteRepository)
        )
    }

    private val binding: FragmentNotesBinding by lazy {
        FragmentNotesBinding.inflate(layoutInflater)
    }

    private val noteListAdapter: NoteListAdapter by lazy {
        NoteListAdapter()
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

        binding.notesRecyclerView.adapter = noteListAdapter
        binding.addButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, AddEditNoteFragment.newInstance(null))
                .addToBackStack(null)
                .commit()
        }

        observe()
    }

    override fun onStart() {
        super.onStart()

        viewModel.loadList()
    }

    private fun observe() {
        viewModel.notesUiState.observe(this) {
            noteListAdapter.submitList(it.noteList)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}