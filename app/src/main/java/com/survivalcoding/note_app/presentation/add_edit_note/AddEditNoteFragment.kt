package com.survivalcoding.note_app.presentation.add_edit_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.core.App
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
            MyViewModelFactory(this, savedInstanceState)
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

        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                AddEditNoteViewModel.UiEvent.SaveNote -> {
                    parentFragmentManager.popBackStack()
                }
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
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