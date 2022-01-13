package com.survivalcoding.noteapp.presentation.add_edit_note

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentAddEditNoteBinding
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.presentation.notes.NotesFragment
import com.survivalcoding.noteapp.presentation.notes.NotesViewModel
import java.util.*

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

        init(arguments)

        binding.rgSelectColor.setOnCheckedChangeListener { _, checkedId ->
            viewModel.setNote(
                color = when (checkedId) {
                    binding.rbColor1.id -> COLOR_1
                    binding.rbColor2.id -> COLOR_2
                    binding.rbColor3.id -> COLOR_3
                    binding.rbColor4.id -> COLOR_4
                    binding.rbColor5.id -> COLOR_5
                    else -> COLOR_1
                }
            )
            binding.clAddEditBackground.setBackgroundColor((Color.parseColor(viewModel.getNote().color)))
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
                viewModel.setNote(
                    title = title,
                    content = content,
                    timeStamp = Date().time,
                )
                viewModel.insert()
                parentFragmentManager.commit {
                    replace(R.id.fragmentContainerView, NotesFragment())
                    setReorderingAllowed(true)
                }
            }
        }
    }

    private fun init(bundle: Bundle?) {
        if (bundle == null) {
            viewModel.setNote(Note())
        } else {
            viewModel.setNote(bundle.getParcelable(NotesFragment.NOTE_KEY) ?: Note())
        }

        binding.etTitleInput.setText(viewModel.getNote().title)
        binding.etContentInput.setText(viewModel.getNote().content)
        binding.clAddEditBackground.setBackgroundColor(Color.parseColor(viewModel.getNote().color))

        when (viewModel.getNote().color) {
            COLOR_1 -> binding.rbColor1.isChecked = true
            COLOR_2 -> binding.rbColor2.isChecked = true
            COLOR_3 -> binding.rbColor3.isChecked = true
            COLOR_4 -> binding.rbColor4.isChecked = true
            COLOR_5 -> binding.rbColor5.isChecked = true
        }
    }

    companion object {
        const val COLOR_1 = "#ffa288"   // -23928
        const val COLOR_2 = "#e3ec96"   // -1839978
        const val COLOR_3 = "#c886d2"   // -3635502
        const val COLOR_4 = "#76d9e6"   // -8988186
        const val COLOR_5 = "#f282a5"   // -884059
    }
}