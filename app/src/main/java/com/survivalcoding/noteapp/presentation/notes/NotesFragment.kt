package com.survivalcoding.noteapp.presentation.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.MainActivity
import com.survivalcoding.noteapp.databinding.FragmentNotesBinding
import com.survivalcoding.noteapp.presentation.add_edit_note.AddEditNoteFragment
import com.survivalcoding.noteapp.presentation.notes.adapter.NoteListAdapter

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<NotesViewModel> {
        NotesViewModelFactory((requireActivity().application as App).noteRepository)
    }
    private val noteListAdapter by lazy {
        NoteListAdapter(
            clickEvent = { note ->
                viewModel.deleteNote(note)
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvNotesRecyclerView.adapter = noteListAdapter

        binding.rgSortBase.setOnCheckedChangeListener { _, checkedId ->
            viewModel.key = when (checkedId) {
                binding.rbBaseTitle.id -> NotesViewModel.ORDER_TITLE
                binding.rbBaseDate.id -> NotesViewModel.ORDER_TIMESTAMP
                else -> NotesViewModel.ORDER_COLOR
            }
            viewModel.sortNotes()
        }

        binding.rgSortMode.setOnCheckedChangeListener { _, checkedId ->
            viewModel.mode = when (checkedId) {
                binding.rbModeAsc.id -> NotesViewModel.ORDER_ASC
                else -> NotesViewModel.ORDER_DESC
            }
            viewModel.sortNotes()
        }

        binding.clSortCondition.visibility = viewModel.filter

        binding.ivDrawerTrigger.setOnClickListener {
            viewModel.filter =
                if (binding.clSortCondition.isVisible) NotesViewModel.FILTER_CLOSE
                else NotesViewModel.FILTER_OPEN

            binding.clSortCondition.visibility = viewModel.filter
        }

        binding.fabAddNewNoteButton.setOnClickListener {
            (requireActivity() as MainActivity).replaceFragment(AddEditNoteFragment())
        }

        viewModel.notes.observe(this) { noteListAdapter.submitList(it) }
    }
}