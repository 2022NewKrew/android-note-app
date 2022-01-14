package com.survivalcoding.noteapp.presentation.add_edit_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import kotlinx.coroutines.flow.collect
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
            viewModel.setColor(
                when (checkedId) {
                    binding.rbColor1.id -> NoteColor.COLOR_1
                    binding.rbColor2.id -> NoteColor.COLOR_2
                    binding.rbColor3.id -> NoteColor.COLOR_3
                    binding.rbColor4.id -> NoteColor.COLOR_4
                    binding.rbColor5.id -> NoteColor.COLOR_5
                    else -> NoteColor.COLOR_1
                }
            )
        }

        binding.fabSaveNoteButton.setOnClickListener {
            val title = binding.etTitleInput.text.toString()
            val content = binding.etContentInput.text.toString()

            val errorMessage = when {
                title.isEmpty() -> "제목을 입력해 주세요."
                content.isEmpty() -> "내용을 입력해 주세요."
                else -> ""
            }

            if (errorMessage.isNotEmpty()) {
                with(requireActivity()) {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            } else {
                viewModel.setTitle(title)
                viewModel.setContent(content)

                viewModel.insert()

                parentFragmentManager.commit {
                    replace(R.id.fragmentContainerView, NotesFragment())
                    setReorderingAllowed(true)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noteState.collect {
                    binding.clAddEditBackground.setBackgroundColor(it.color.toInt())
                    binding.etTitleInput.setText(it.title)
                    binding.etContentInput.setText(it.content)
                    binding.rgSelectColor.check(it.color.toId())
                }
            }
        }
    }

    private fun setNote() {
        arguments?.let {
            val note = it.getParcelable(NotesFragment.NOTE_KEY) ?: Note()

            viewModel.setId(note.id)
            viewModel.setTitle(note.title)
            viewModel.setContent(note.content)
            viewModel.setColor(note.color)
        }
    }
}