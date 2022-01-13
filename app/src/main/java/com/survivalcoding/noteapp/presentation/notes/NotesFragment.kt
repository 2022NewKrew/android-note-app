package com.survivalcoding.noteapp.presentation.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.NoteApplication
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNotesBinding
import com.survivalcoding.noteapp.presentation.AppViewModelFactory
import com.survivalcoding.noteapp.presentation.notes.adapter.NotesAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<NotesViewModel> {
        AppViewModelFactory(requireActivity().application as NoteApplication)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.orderByRg.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.orderByTitleRb -> viewModel.setOrderBy(NoteOrderBy.TITLE)
                R.id.orderByDateRb -> viewModel.setOrderBy(NoteOrderBy.DATE)
                R.id.orderByColorRb -> viewModel.setOrderBy(NoteOrderBy.COLOR)
            }
        }

        binding.isAscRg.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.isAscTrueRb -> viewModel.setIsAsc(true)
                R.id.isAscFalseRb -> viewModel.setIsAsc(false)
            }
        }

        val notesAdapter = NotesAdapter({
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToNoteFragment(it.id!!))
        }, {
            viewModel.deleteNote(it)
        }).apply {
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                    binding.notesRv.scrollToPosition(0)
                }
            })
        }
        binding.notesRv.adapter = notesAdapter

        binding.addFab.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToNoteFragment(0))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    notesAdapter.submitList(it.notes)
                    binding.orderByRg.check(it.orderBy.toId())
                    binding.isAscRg.check(if(it.isAsc) R.id.isAscTrueRb else R.id.isAscFalseRb)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun NoteOrderBy.toId(): Int {
        return when(this) {
            NoteOrderBy.TITLE -> R.id.orderByTitleRb
            NoteOrderBy.DATE -> R.id.orderByDateRb
            NoteOrderBy.COLOR -> R.id.orderByColorRb
        }
    }
}