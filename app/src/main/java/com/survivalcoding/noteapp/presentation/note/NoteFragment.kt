package com.survivalcoding.noteapp.presentation.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.survivalcoding.noteapp.NoteApplication
import com.survivalcoding.noteapp.databinding.FragmentNoteBinding
import com.survivalcoding.noteapp.presentation.AppViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels {
        AppViewModelFactory(requireActivity().application as NoteApplication)
    }

    private val args: NoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getNoteById(args.noteId)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.note.collect {
                    it?.run {
                        binding.titleTv.setText(title)
                        binding.contentTv.setText(content)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}