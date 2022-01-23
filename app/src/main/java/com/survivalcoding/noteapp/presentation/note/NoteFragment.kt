package com.survivalcoding.noteapp.presentation.note

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.survivalcoding.noteapp.NoteApplication
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNoteBinding
import com.survivalcoding.noteapp.presentation.AppViewModelFactory
import com.survivalcoding.noteapp.presentation.ColorRadioButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

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
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.colorsRg.setOnCheckedChangeListener { _, id: Int ->
            when (id) {
                R.id.lightSalmonCrb -> {
                    viewModel.setColor(binding.lightSalmonCrb.color)
                }
                R.id.primroseCrb -> {
                    viewModel.setColor(binding.primroseCrb.color)
                }
                R.id.pastelVioletCrb -> {
                    viewModel.setColor(binding.pastelVioletCrb.color)
                }
                R.id.aquamarineBlueCrb -> {
                    viewModel.setColor(binding.aquamarineBlueCrb.color)
                }
                R.id.pinkSherbetCrb -> {
                    viewModel.setColor(binding.pinkSherbetCrb.color)
                }
            }
        }

        binding.titleEv.doAfterTextChanged {
            viewModel.setTitle(it.toString())
        }

        binding.contentEv.doAfterTextChanged {
            viewModel.setContent(it.toString())
        }

        binding.saveFab.setOnClickListener {
            viewModel.saveNote()
        }

        viewModel.setNote(args.note)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    binding.titleEv.setTextIfDiff(it.note.title)
                    binding.contentEv.setTextIfDiff(it.note.content)
                    val crbId = getColorRadioButtonIdFromColor(it.note.color)
                    binding.colorsRg.check(crbId)
                    binding.root.setBackgroundColor(
                        binding.colorsRg.findViewById<ColorRadioButton>(
                            crbId
                        ).color
                    )
                    if (it.saved) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun EditText.setTextIfDiff(text: String) {
        if(this.text.toString() != text) {
            setText(text)
        }
    }

    private fun getColorRadioButtonIdFromColor(color: Int): Int {
        return binding.colorsRg.children.filter { it is ColorRadioButton }
            .find {
                (it as ColorRadioButton).color == color
            }?.id ?: R.id.lightSalmonCrb
    }
}