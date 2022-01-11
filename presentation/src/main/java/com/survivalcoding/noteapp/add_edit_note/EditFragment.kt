package com.survivalcoding.noteapp.add_edit_note

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.domain.entity.Note
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.MainActivity
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.EditFragmentBinding
import java.util.*

class EditFragment : Fragment() {

    private lateinit var viewModel: AddEditNoteViewModel
    private lateinit var binding: EditFragmentBinding
    private var isTitleEmpty = true
    private var isContentEmpty = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            AddEditNoteViewModelFactory((requireActivity().application as App).repository)
        ).get(AddEditNoteViewModel::class.java)

        var note: Note? = null
        setFragmentResultListener(MainActivity.FRAGMENT_KEY) { requestKey, bundle ->
            note = bundle.getParcelable("note")
            note?.let {
                binding.titleInputView.setText(it.title)
                binding.contentInputView.setText(it.content)
            }
        }

        // input handling
        binding.inputBtn.isEnabled = false
        binding.titleInputView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    isTitleEmpty = it.isEmpty()
                    checkBtnEnable()
                }
            }

        })
        binding.contentInputView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    isContentEmpty = it.isEmpty()
                    checkBtnEnable()
                }
            }

        })
        binding.inputBtn.setOnClickListener {
            if (note != null) {
                note?.let {
                    val newNote = it.copy(
                        title = binding.titleInputView.text.toString(),
                        content = binding.contentInputView.text.toString(),
                        color = Color.RED,
                        timestamp = Date().time
                    )
                    viewModel.updateNote(newNote)
                }
            } else {
                val newNote = Note(
                    title = binding.titleInputView.text.toString(),
                    content = binding.contentInputView.text.toString(),
                    color = Color.RED,
                    timestamp = Date().time
                )
                viewModel.insertNote(newNote)
            }
            parentFragmentManager.popBackStack()
        }

    }

    fun checkBtnEnable() {
        binding.inputBtn.isEnabled = !isTitleEmpty && !isContentEmpty
    }

}