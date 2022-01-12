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
                    putParcelable(NOTE_KEY, note)
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

        initSortCondition()

        // 리사이클러뷰 연결
        binding.rvNotesRecyclerView.adapter = noteListAdapter

        // title, timestamp, color 기준으로 정렬
        binding.rgSortBase.setOnCheckedChangeListener { _, checkedId ->
            val key = when (checkedId) {
                binding.rbBaseTitle.id -> UiState.ORDER_TITLE
                binding.rbBaseDate.id -> UiState.ORDER_TIMESTAMP
                else -> UiState.ORDER_COLOR
            }
            viewModel.setUi(key = key)
            noteListAdapter.submitSortedList(uiState = viewModel.getUiState())
        }

        // 오름차순, 내림차순 정렬
        binding.rgSortMode.setOnCheckedChangeListener { _, checkedId ->
            val mode = when (checkedId) {
                binding.rbModeAsc.id -> UiState.ORDER_ASC
                else -> UiState.ORDER_DESC
            }
            viewModel.setUi(mode = mode)
            noteListAdapter.submitSortedList(uiState = viewModel.getUiState())
        }

        // 정렬 기능 숨김 처리
        binding.clSortCondition.visibility = viewModel.getUiState().sortVisible

        // 정렬 기능 표시/비표시
        binding.ivDrawerTrigger.setOnClickListener {
            val isVisible =
                if (binding.clSortCondition.isVisible) UiState.FILTER_CLOSE
                else UiState.FILTER_OPEN

            viewModel.setUi(isVisible = isVisible)
            binding.clSortCondition.visibility = viewModel.getUiState().sortVisible
        }

        // 노트 추가 버튼 설정
        binding.fabAddNewNoteButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragmentContainerView, AddEditNoteFragment())
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        viewModel.notes.observe(this) {
            noteListAdapter.submitSortedList(it, viewModel.getUiState())
        }
    }

    private fun initSortCondition() {
        when (viewModel.getUiState().sortKey) {
            UiState.ORDER_TITLE -> binding.rbBaseTitle.isChecked = true
            UiState.ORDER_TIMESTAMP -> binding.rbBaseDate.isChecked = true
            UiState.ORDER_COLOR -> binding.rbBaseColor.isChecked = true
            else -> {}
        }
        when (viewModel.getUiState().sortMode) {
            UiState.ORDER_ASC -> binding.rbModeAsc.isChecked = true
            UiState.ORDER_DESC -> binding.rbModeDesc.isChecked = true
            else -> {}
        }
    }

    companion object {
        const val NOTE_KEY = "note_key"
    }
}