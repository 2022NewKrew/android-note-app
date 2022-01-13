package com.survivalcoding.noteapp.presentation.add_edit_note

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentAddEditNoteBinding
import com.survivalcoding.noteapp.domain.model.Color
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase
import com.survivalcoding.noteapp.presentation.color2ColorResourceId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddEditNoteFragment : Fragment() {

    private val viewModel: AddEditNoteViewModel by viewModels {
        val noteRepository = (requireActivity().application as App).noteRepository
        AddEditNoteViewModelFactory(InsertNoteUseCase(noteRepository))
    }

    private val binding: FragmentAddEditNoteBinding by lazy {
        FragmentAddEditNoteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getParcelable<Note>(NOTE)?.let {
            viewModel.setId(it.id)
            viewModel.setTitleText(SpannableStringBuilder(it.title))
            viewModel.setContentText(SpannableStringBuilder(it.content))
            viewModel.setColor(Color.fromInt(it.color))
        }
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
            setTitleText(it.title)
            setContentText(it.content)
            setSelectedColorButton(it.color)
            setBackgroundColor(it.color)
        }

        repeatOnStart {
            viewModel.eventFlow.collect { handleEvent(it) }
        }
    }

    private fun handleEvent(event: AddEditNoteViewModel.Event) {
        when (event) {
            is AddEditNoteViewModel.Event.ShowSnackBarEvent -> {
                showSnackBar(
                    event.messageResourceId,
                    event.actionTextResourceId,
                    event.action
                )
            }
            is AddEditNoteViewModel.Event.NavigateToNotesEvent -> navigateToNote()
        }
    }

    private fun repeatOnStart(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    private fun navigateToNote() {
        parentFragmentManager.popBackStack()
    }

    private fun showSnackBar(
        messageResourceId: Int,
        actionTextResourceId: Int?,
        action: View.OnClickListener?
    ) {
        val snackBar = Snackbar.make(binding.root, messageResourceId, Snackbar.LENGTH_SHORT)
        if (actionTextResourceId != null && action != null) {
            snackBar.setAction(actionTextResourceId, action)
        }
        snackBar.show()
    }

    private fun setTitleText(title: Editable) {
        if (binding.titleEditText.text != title) {
            binding.titleEditText.text = title
        }
    }

    private fun setContentText(content: Editable) {
        if (binding.contentEditText.text != content) {
            binding.contentEditText.text = content
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
        binding.root.setBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                color2ColorResourceId(color),
                null
            )
        )
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
        private const val NOTE = "NOTE"

        @JvmStatic
        fun newInstance(note: Note?) = AddEditNoteFragment().apply {
            arguments = Bundle().apply {
                if (note != null) {
                    putParcelable(NOTE, note)
                }
            }
        }
    }
}