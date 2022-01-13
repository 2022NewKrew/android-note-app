package com.survivalcoding.noteapp.presentation.add_edit_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentAddEditNoteBinding
import com.survivalcoding.noteapp.domain.model.Color
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.usecase.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase

class AddEditNoteFragment : Fragment() {

    private val viewModel: AddEditNoteViewModel by viewModels {
        val noteRepository = (requireActivity().application as App).noteRepository
        AddEditNoteViewModelFactory(
            InsertNoteUseCase(noteRepository),
            DeleteNoteUseCase(noteRepository)
        )
    }

    private val binding: FragmentAddEditNoteBinding by lazy {
        FragmentAddEditNoteBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleEditText.doAfterTextChanged {
            if (it == viewModel.editNoteUiState.value?.title) return@doAfterTextChanged

            viewModel.setTitleText(it ?: return@doAfterTextChanged)
        }

        binding.contentEditText.doAfterTextChanged {
            if (it == viewModel.editNoteUiState.value?.content) return@doAfterTextChanged

            viewModel.setContentText(it ?: return@doAfterTextChanged)
        }

        binding.saveButton.setOnClickListener {
            viewModel.saveNote()
            parentFragmentManager.popBackStack()
        }

        binding.colorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedColor = when (checkedId) {
                R.id.yellow_radio_button -> Color.YELLOW
                R.id.blue_radio_button -> Color.BLUE
                R.id.lavender_radio_button -> Color.LAVENDER
                R.id.purple_radio_button -> Color.PURPLE
                R.id.red_radio_button -> Color.RED
                else -> return@setOnCheckedChangeListener
            }
            viewModel.setColor(selectedColor)
        }

        observe()
    }

    private fun observe() {
        viewModel.editNoteUiState.observe(this) {
            setSelectedColorButton(it.color)
            setBackgroundColor(it.color)
        }
    }

    private fun setSelectedColorButton(color: Color) {
        val selectedButton = when (color) {
            Color.YELLOW -> binding.yellowRadioButton
            Color.BLUE -> binding.blueRadioButton
            Color.LAVENDER -> binding.lavenderRadioButton
            Color.PURPLE -> binding.purpleRadioButton
            Color.RED -> binding.redRadioButton
        }
        if (!selectedButton.isChecked) selectedButton.isChecked = true
    }

    private fun setBackgroundColor(color: Color) {
        val colorResourceId = when (color) {
            Color.YELLOW -> R.color.yellow
            Color.BLUE -> R.color.blue
            Color.LAVENDER -> R.color.lavender
            Color.PURPLE -> R.color.purple
            Color.RED -> R.color.red
        }
        binding.root.setBackgroundColor(ResourcesCompat.getColor(resources, colorResourceId, null))
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

    companion object {
        @JvmStatic
        fun newInstance(note: Note?) = AddEditNoteFragment().apply {
            arguments = Bundle().apply {
//                putParcelable()
            }
        }
    }
}