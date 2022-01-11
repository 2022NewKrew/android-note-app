package com.survivalcoding.noteapp.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNotesBinding
import com.survivalcoding.noteapp.presentation.add_edit_note.AddEditNoteFragment
import com.survivalcoding.noteapp.presentation.notes.adapter.NoteListAdapter
import java.util.*

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<NotesViewModel> {
        NotesViewModelFactory((requireActivity().application as App).noteRepository)
    }
    private val noteListAdapter by lazy {
        NoteListAdapter(
            deleteClickEvent = { note ->
                viewModel.deleteNote(note)
            },
            itemClickEvent = { note ->
                val bundle = Bundle().apply {
                    putParcelable(NotesViewModel.NOTE_KEY, note)
                }
                parentFragmentManager.commit {
                    replace(
                        R.id.fragmentContainerView,
                        AddEditNoteFragment().apply { arguments = bundle })
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
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

        viewModel.refreshNoteList()

        initSortCondition()

        // 리사이클러뷰 연결
        binding.rvNotesRecyclerView.adapter = noteListAdapter

        // title, timestamp, color 기준으로 정렬
        binding.rgSortBase.setOnCheckedChangeListener { _, checkedId ->
            viewModel.key = when (checkedId) {
                binding.rbBaseTitle.id -> NotesViewModel.ORDER_TITLE
                binding.rbBaseDate.id -> NotesViewModel.ORDER_TIMESTAMP
                else -> NotesViewModel.ORDER_COLOR
            }
            viewModel.sortNotes()
            binding.rvNotesRecyclerView.scrollToPosition(-1)
        }

        // 오름차순, 내림차순 정렬
        binding.rgSortMode.setOnCheckedChangeListener { _, checkedId ->
            viewModel.mode = when (checkedId) {
                binding.rbModeAsc.id -> NotesViewModel.ORDER_ASC
                else -> NotesViewModel.ORDER_DESC
            }
            viewModel.sortNotes()
            binding.rvNotesRecyclerView.scrollToPosition(0)
        }

        // 정렬 기능 숨김 처리
        binding.clSortCondition.visibility = viewModel.filter

        // 정렬 기능 표시/비표시
        binding.ivDrawerTrigger.setOnClickListener {
            viewModel.filter =
                if (binding.clSortCondition.isVisible) NotesViewModel.FILTER_CLOSE
                else NotesViewModel.FILTER_OPEN

            binding.clSortCondition.visibility = viewModel.filter
        }

        // 노트 추가 버튼 설정
        binding.fabAddNewNoteButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragmentContainerView, AddEditNoteFragment())
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        viewModel.notes.observe(this) { noteListAdapter.submitList(it) }
    }

    private fun initSortCondition() {
        when (viewModel.key) {
            NotesViewModel.ORDER_TITLE -> binding.rbBaseTitle.isChecked = true
            NotesViewModel.ORDER_TIMESTAMP -> binding.rbBaseDate.isChecked = true
            NotesViewModel.ORDER_COLOR -> binding.rbBaseColor.isChecked = true
        }
        when (viewModel.mode) {
            NotesViewModel.ORDER_ASC -> binding.rbModeAsc.isChecked = true
            NotesViewModel.ORDER_DESC -> binding.rbModeDesc.isChecked = true
        }
    }
}