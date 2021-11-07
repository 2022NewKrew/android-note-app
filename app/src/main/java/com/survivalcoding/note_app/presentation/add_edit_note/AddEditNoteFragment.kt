package com.survivalcoding.note_app.presentation.add_edit_note

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.note_app.App
import com.survivalcoding.note_app.R
import com.survivalcoding.note_app.databinding.FragmentAddEditNoteBinding

class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {
    private var _binding: FragmentAddEditNoteBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: AddEditNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(this, arguments ?: savedInstanceState)
        )[AddEditNoteViewModel::class.java]

        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            viewModel.onEvent(
                AddEditNoteEvent.SaveNote(
                    title = binding.titleEditText.text.toString(),
                    content = binding.contentEditText.text.toString(),
                )
            )
        }

        val circles = listOf(
            Pair(binding.imageView, ResourcesCompat.getColor(resources, R.color.roseBud, null)),
            Pair(binding.imageView2, ResourcesCompat.getColor(resources, R.color.primrose, null)),
            Pair(binding.imageView3, ResourcesCompat.getColor(resources, R.color.wisteria, null)),
            Pair(binding.imageView4, ResourcesCompat.getColor(resources, R.color.skyBlue, null)),
            Pair(binding.imageView5, ResourcesCompat.getColor(resources, R.color.illusion, null)),
        )

        circles.forEach { circle ->
            circle.first.setOnClickListener { view ->
                view.isSelected = true

                circles.filter { it.first.id != view.id }
                    .forEach { it.first.isSelected = false }

                val startDrawable = ColorDrawable(viewModel.noteColor.value ?: Color.WHITE)
                val endDrawable = ColorDrawable(circle.second)
                val transitionDrawable = TransitionDrawable(arrayOf(startDrawable, endDrawable))
                binding.background.background = transitionDrawable
                transitionDrawable.startTransition(500)

                viewModel.onEvent(AddEditNoteEvent.ChangeColor(circle.second))
            }
        }

        viewModel.noteColor.observe(viewLifecycleOwner) { noteColor ->
            binding.background.setBackgroundColor(noteColor)
        }
        viewModel.noteTitle.observe(viewLifecycleOwner) { title ->
            binding.titleEditText.setText(title)
        }
        viewModel.noteContent.observe(viewLifecycleOwner) { content ->
            binding.contentEditText.setText(content)
        }

        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                AddEditNoteViewModel.UiEvent.SaveNote -> {
                    parentFragmentManager.popBackStack()
                }
                is AddEditNoteViewModel.UiEvent.ShowSnackBar -> {
                    Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG)
                        .setAnchorView(binding.saveButton)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class MyViewModelFactory(owner: SavedStateRegistryOwner, defaultArgs: Bundle? = null) :
        AbstractSavedStateViewModelFactory(
            owner, defaultArgs
        ) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            if (modelClass.isAssignableFrom(AddEditNoteViewModel::class.java)) {
                val repository = (requireActivity().application as App).repository

                return AddEditNoteViewModel(repository, handle) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}