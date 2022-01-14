package com.survivalcoding.noteapp.presentation.note.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.survivalcoding.noteapp.databinding.FragmentNoteListBinding
import com.survivalcoding.noteapp.presentation.note.list.adapter.NoteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : Fragment() {
  private var _binding: FragmentNoteListBinding? = null
  private val binding get() = _binding!!

  private val viewModel by viewModels<NoteListViewModel>()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentNoteListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val adapter = NoteListAdapter()

    binding.recyclerViewNoteList.adapter = adapter

    viewModel.getNotes().observe(viewLifecycleOwner) {
      adapter.submitList(it)
    }

    binding.addNoteButton.setOnClickListener {
      val action = NoteListFragmentDirections.actionEditNote()
      it.findNavController().navigate(action)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
