package com.survivalcoding.noteapp.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNotesBinding
import com.survivalcoding.noteapp.presentation.add_edit_note.AddEditNoteFragment
import com.survivalcoding.noteapp.presentation.notes.adapter.NoteListAdapter


class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<NotesViewModel> {
        NotesViewModelFactory(
            application = requireActivity().application,
            notesRepository = (requireActivity().application as App).notesRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        val adapter = NoteListAdapter(onClickDeleteButton = { note ->
            viewModel.deleteNote(note)
            Snackbar.make(view, "노트 삭제", Snackbar.LENGTH_LONG)
                .setAction("되돌리기") { viewModel.insertNote(note) }
                .show()
        }, onClickView = {
            moveToAddEditNoteFragment(it.id)
        })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        viewModel.notes.observe(this) {
            adapter.submitList(it)
        }

        val alignButton = binding.alignButton
        val filterLayout = binding.filterLayout
        alignButton.setOnClickListener {
            if (filterLayout.visibility == VISIBLE) filterLayout.visibility = GONE
            else filterLayout.visibility = VISIBLE
        }
        //ToDo: 정렬 기능 구현
        val addButton = binding.addButton
        addButton.setOnClickListener {
            moveToAddEditNoteFragment()
        }
    }

    private fun moveToAddEditNoteFragment(id: Int = -1) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_view,
                AddEditNoteFragment().apply {
                    this.arguments = bundleOf(MODIFY to id)
                })
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val MODIFY = "modify"

        const val SORT_ASC = 0
        const val SORT_DESC = 1

        const val BY_TITLE = 0
        const val BY_DATE = 1
        const val BY_COLOR = 2
    }
}