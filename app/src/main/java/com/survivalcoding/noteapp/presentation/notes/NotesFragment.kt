package com.survivalcoding.noteapp.presentation.notes

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNotesBinding
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order
import com.survivalcoding.noteapp.domain.usecase.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.usecase.GetNotesByOrderUseCase
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase
import com.survivalcoding.noteapp.presentation.add_edit_note.AddEditNoteFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesFragment : Fragment() {

    private val viewModel: NotesViewModel by viewModels {
        val noteRepository = (requireActivity().application as App).noteRepository
        NotesViewModelFactory(
            GetNotesByOrderUseCase(noteRepository),
            DeleteNoteUseCase(noteRepository),
            InsertNoteUseCase(noteRepository)
        )
    }

    private val binding: FragmentNotesBinding by lazy {
        FragmentNotesBinding.inflate(layoutInflater)
    }

    private val noteListAdapter: NoteListAdapter by lazy {
        NoteListAdapter(
            { note -> viewModel.navigateToEditNote(note) },
            { note -> viewModel.deleteNote(note) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.notes, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.show_sort_section_button) {
            println("hello")
            binding.sortByRadioGroup.isVisible = !binding.sortByRadioGroup.isVisible
            binding.sortOrderRadioGroup.isVisible = !binding.sortOrderRadioGroup.isVisible
            true
        } else {
            super.onOptionsItemSelected(item)
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

        binding.notesRecyclerView.adapter = noteListAdapter

        binding.addButton.setOnClickListener { viewModel.navigateToAddNote() }

        binding.sortByRadioGroup.setOnCheckedChangeListener { _, _ -> sortNotes() }
        binding.sortOrderRadioGroup.setOnCheckedChangeListener { _, _ -> sortNotes() }

        observe()
    }

    private fun sortNotes() {
        val isAscending =
            binding.sortOrderRadioGroup.checkedRadioButtonId == R.id.ascending_radio_button

        when (binding.sortByRadioGroup.checkedRadioButtonId) {
            R.id.title_radio_button -> {
                viewModel.setOrder(if (isAscending) Order.TITLE_ASC else Order.TITLE_DESC)
            }
            R.id.date_radio_button -> {
                viewModel.setOrder(if (isAscending) Order.DATE_ASC else Order.DATE_DESC)
            }
            R.id.color_radio_button -> {
                viewModel.setOrder(if (isAscending) Order.COLOR_ASC else Order.COLOR_DESC)
            }
        }
    }

    private fun observe() {
        viewModel.notesUiState.observe(this) {
            noteListAdapter.submitList(it.noteList)
            setSelectedOrderRadioButton(it.orderBy)
        }

        repeatOnStart {
            viewModel.eventFlow.collect { handleEvent(it) }
        }
    }

    private fun repeatOnStart(block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    private fun setSelectedOrderRadioButton(order: Order) {
        if (Order.isAsc(order)) {
            binding.ascendingRadioButton.isChecked = true
        } else {
            binding.descendingRadioButton.isChecked = true
        }

        when (order) {
            Order.COLOR_DESC, Order.COLOR_ASC -> {
                binding.colorRadioButton.isChecked = true
            }
            Order.DATE_DESC, Order.DATE_ASC -> {
                binding.dateRadioButton.isChecked = true
            }
            Order.TITLE_DESC, Order.TITLE_ASC -> {
                binding.titleRadioButton.isChecked = true
            }
        }
    }

    private fun handleEvent(event: NotesViewModel.Event) {
        when (event) {
            is NotesViewModel.Event.NavigateToEditNote -> navigateToEditNote(event.note)
            is NotesViewModel.Event.NavigateToAddNote -> navigateToAddNote()
            is NotesViewModel.Event.ShowSnackBarEvent -> {
                showSnackBar(
                    event.messageResourceId,
                    event.actionTextResourceId,
                    event.action
                )
            }
        }
    }

    private fun navigateToAddNote() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, AddEditNoteFragment.newInstance(null))
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToEditNote(note: Note) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, AddEditNoteFragment.newInstance(note))
            .addToBackStack(null)
            .commit()
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

    override fun onStart() {
        super.onStart()

        viewModel.loadList()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}