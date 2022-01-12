package com.survivalcoding.noteapp.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNotesBinding
import com.survivalcoding.noteapp.presentation.add_edit_note.AddEditNoteFragment

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<NotesViewModel> {
        NotesViewModelFactory(
            application = requireActivity().application,
            notesRepository = (requireActivity().application as App).notesRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //ToDo: RecyclerView 구현 및 관련 클래스 규정

        val alignButton = binding.alignButton
        val filterLayout = binding.filterLayout
        alignButton.setOnClickListener {
            if (filterLayout.visibility == VISIBLE) filterLayout.visibility = GONE
            else filterLayout.visibility = VISIBLE
        }
        val addButton = binding.addButton
        addButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container_view,
                    AddEditNoteFragment().apply {
                        this.arguments = bundleOf(MODIFY to -1)
                    })
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        const val MODIFY = "modify"

        const val SORT_ASC = 0
        const val SORT_DESC = 1

        const val BY_TITLE = 0
        const val BY_DATE = 1
        const val BY_COLOR = 2
    }
}