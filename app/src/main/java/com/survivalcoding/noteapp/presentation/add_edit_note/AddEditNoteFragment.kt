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

        binding.rgSelectColor.setOnCheckedChangeListener { _, checkedId ->
            viewModel.onEvent(idToSetColorEvent(checkedId))
        }

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
                binding.rgSelectColor.check(it.color.toId())
            }
        }
    }

    private fun repeatOnStart(block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    private fun idToSetColorEvent(id: Int): AddEditNoteEvent.SetColor {
        val color = when (id) {
            binding.rbColor1.id -> NoteColor.COLOR_1
            binding.rbColor2.id -> NoteColor.COLOR_2
            binding.rbColor3.id -> NoteColor.COLOR_3
            binding.rbColor4.id -> NoteColor.COLOR_4
            binding.rbColor5.id -> NoteColor.COLOR_5
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