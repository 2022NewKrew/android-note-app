package com.survivalcoding.noteapp.presentation.add_edit_note

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.MainActivity
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentAddEditNoteBinding
import com.survivalcoding.noteapp.presentation.notes.NotesFragment
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

        init(savedInstanceState)

        binding.clAddEditBackground.setBackgroundColor(Color.parseColor(viewModel.backgroundColor))

        binding.rgSelectColor.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbColor1.id -> {
                    viewModel.backgroundColor = COLOR_1
                    binding.clAddEditBackground.setBackgroundColor(Color.parseColor(COLOR_1))
                }
                binding.rbColor2.id -> {
                    viewModel.backgroundColor = COLOR_2
                    binding.clAddEditBackground.setBackgroundColor(Color.parseColor(COLOR_2))
                }
                binding.rbColor3.id -> {
                    viewModel.backgroundColor = COLOR_3
                    binding.clAddEditBackground.setBackgroundColor(Color.parseColor(COLOR_3))
                }
                binding.rbColor4.id -> {
                    viewModel.backgroundColor = COLOR_4
                    binding.clAddEditBackground.setBackgroundColor(Color.parseColor(COLOR_4))
                }
                binding.rbColor5.id -> {
                    viewModel.backgroundColor = COLOR_5
                    binding.clAddEditBackground.setBackgroundColor(Color.parseColor(COLOR_5))
                }
            }
        }

        binding.fabSaveNoteButton.setOnClickListener {
            viewModel.title = binding.etTitleInput.text.toString()
            viewModel.content = binding.etContentInput.text.toString()

            val errorMessage = when {
                viewModel.title.isEmpty() -> "제목을 입력해 주세요."
                viewModel.content.isEmpty() -> "내용을 입력해 주세요."
                else -> ""
            }

            if (errorMessage.isNotEmpty()) {
                with(requireActivity()) {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            } else {
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
            viewModel.id = Date().time
            viewModel.title = ""
            viewModel.content = ""
            viewModel.backgroundColor = COLOR_1
        } else {
            viewModel.id = bundle.getLong(NotesFragment.ID, Date().time)
            viewModel.title = bundle.getString(NotesFragment.TITLE) ?: ""
            viewModel.content = bundle.getString(NotesFragment.CONTENT) ?: ""
            viewModel.backgroundColor = bundle.getString(NotesFragment.COLOR, COLOR_1)
        }
    }

    companion object {
        const val COLOR_1 = "#ffa288"
        const val COLOR_2 = "#e3ec96"
        const val COLOR_3 = "#c886d2"
        const val COLOR_4 = "#76d9e6"
        const val COLOR_5 = "#f282a5"
    }
}