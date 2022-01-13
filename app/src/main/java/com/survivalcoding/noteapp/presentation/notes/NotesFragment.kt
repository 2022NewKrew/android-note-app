package com.survivalcoding.noteapp.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNotesBinding
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.usecase.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.usecase.GetNotesByOrderUseCase
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase
import com.survivalcoding.noteapp.presentation.add_edit_note.AddEditNoteFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesFragment : Fragment() {

    private val viewModel: NotesViewModel by viewModels {
        val noteRepository = (requireActivity().application as App).noteRepository
        NotesViewModelFactory(
            GetNotesByOrderUseCase(noteRepository),
            DeleteNoteUseCase(noteRepository),
            InsertNoteUseCase(noteRepository)
        )
    }

    private val binding: FragmentNotesBinding by lazy {
        FragmentNotesBinding.inflate(layoutInflater)
    }

    private val noteListAdapter: NoteListAdapter by lazy {
        NoteListAdapter(
            { note -> viewModel.navigateToEditNote(note) },
            { note -> viewModel.deleteNote(note) }
        )
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
        binding.addButton.setOnClickListener { viewModel.navigateToAddNote() }

        observe()
    }

    private fun observe() {
        viewModel.notesUiState.observe(this) {
            noteListAdapter.submitList(it.noteList)
        }

        repeatOnStart {
            viewModel.eventFlow.collect { handleEvent(it) }
        }
    }

    private fun repeatOnStart(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    private fun handleEvent(event: NotesViewModel.Event) {
        when (event) {
            is NotesViewModel.Event.NavigateToEditNote -> navigateToEditNote(event.note)
            is NotesViewModel.Event.NavigateToAddNote -> navigateToAddNote()
            is NotesViewModel.Event.ShowSnackBarEvent -> {
                showSnackBar(
                    event.messageResourceId,
                    event.actionTextResourceId,
                    event.action
                )
            }
        }
    }

    private fun navigateToAddNote() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, AddEditNoteFragment.newInstance(null))
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToEditNote(note: Note) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, AddEditNoteFragment.newInstance(note))
            .addToBackStack(null)
            .commit()
    }

    private fun showSnackBar(
        messageResourceId: Int,
        actionTextResourceId: Int?,
        action: View.OnClickListener?
    ) {
        val snackBar = Snackbar.make(binding.root, messageResourceId, Snackbar.LENGTH_SHORT)
        if (actionTextResourceId != null && action != null) {
            snackBar.setAction(actionTextResourceId, action)
        }
        snackBar.show()
    }

    override fun onStart() {
        super.onStart()

        viewModel.loadList()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}