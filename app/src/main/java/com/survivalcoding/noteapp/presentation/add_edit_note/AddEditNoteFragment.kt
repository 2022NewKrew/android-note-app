package com.survivalcoding.noteapp.presentation.add_edit_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentAddEditNoteBinding
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.NoteColor
import com.survivalcoding.noteapp.presentation.add_edit_note.adapter.ColorListAdapter
import com.survivalcoding.noteapp.presentation.notes.NotesFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddEditNoteFragment : Fragment() {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<AddEditNoteViewModel> {
        AddEditNoteViewModelFactory((requireActivity().application as App).noteRepository)
    }

    private val adapter by lazy {
        ColorListAdapter(
            itemClickEvent = {
                viewModel.onEvent(idToSetColorEvent(it.id))
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNote()

        binding.rvSelectColor.adapter = adapter

        binding.fabSaveNoteButton.setOnClickListener {
            val title = binding.etTitleInput.text.toString()
            val content = binding.etContentInput.text.toString()

            viewModel.onEvent(AddEditNoteEvent.SetTitle(title))
            viewModel.onEvent(AddEditNoteEvent.SetContent(content))
            viewModel.onEvent(AddEditNoteEvent.InsertNote)

            parentFragmentManager.commit {
                replace(R.id.fragmentContainerView, NotesFragment())
                setReorderingAllowed(true)
            }

        }

        collect()
    }

    private fun collect() {
        repeatOnStart {
            viewModel.noteState.collectLatest {
                binding.clAddEditBackground.setBackgroundColor(it.color.toInt())
                binding.etTitleInput.setText(it.title)
                binding.etContentInput.setText(it.content)
                adapter.submitList(it.backgroundColor)
            }
        }
    }

    private fun repeatOnStart(block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    private fun idToSetColorEvent(id: Long?): AddEditNoteEvent.SetColor {
        val color = when (id) {
            0L -> NoteColor.COLOR_1
            1L -> NoteColor.COLOR_2
            2L -> NoteColor.COLOR_3
            3L -> NoteColor.COLOR_4
            4L -> NoteColor.COLOR_5
            else -> NoteColor.COLOR_1
        }
        return AddEditNoteEvent.SetColor(color)
    }

    private fun setNote() {
        arguments?.let {
            val note = it.getParcelable(NotesFragment.NOTE_KEY) ?: Note()

            viewModel.onEvent(AddEditNoteEvent.SetId(note.id))
            viewModel.onEvent(AddEditNoteEvent.SetTitle(note.title))
            viewModel.onEvent(AddEditNoteEvent.SetContent(note.content))
            viewModel.onEvent(AddEditNoteEvent.SetColor(note.color))
        }
    }
}