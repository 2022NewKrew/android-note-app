package com.survivalcoding.noteapp.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.MainActivity
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.add_edit_note.EditFragment
import com.survivalcoding.noteapp.databinding.MainFragmentBinding
import com.survivalcoding.noteapp.notes.adapter.NoteListAdapter
import com.survivalcoding.noteapp.notes.adapter.NoteSwipeHandler
import com.survivalcoding.noteapp.presentation.notes.NotesViewModel
import com.survivalcoding.noteapp.presentation.notes.NotesViewModelFactory

class MainFragment : Fragment() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var binding: MainFragmentBinding
    private var SORTED_WITH = "TIME"
    private var ASC_OR_DESC = "ASC"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this, NotesViewModelFactory(
                (requireActivity().application as App).repository
            )
        ).get(NotesViewModel::class.java)

        val adapter = NoteListAdapter(
            onLongClicked = { note ->
                setFragmentResult(MainActivity.FRAGMENT_KEY, bundleOf("note" to note))
                parentFragmentManager.commit {
                    add<EditFragment>(R.id.fragment_container_view)
                    addToBackStack(null)
                }
            },
            onLeftSwiped = { note ->
                viewModel.deleteNote(note)
            }
        )

        binding.noteListView.layoutManager = LinearLayoutManager(requireContext())
        binding.noteListView.adapter = adapter

        val callback: ItemTouchHelper.Callback = NoteSwipeHandler(adapter)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(binding.noteListView)

        viewModel.notes.asLiveData().observe(this) {
            adapter.submitList(it)
        }

        binding.fab.setOnClickListener {
            parentFragmentManager.commit {
                add<EditFragment>(R.id.fragment_container_view)
                addToBackStack(null)
            }
        }

        // sorting
        binding.contentSortGroupView.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.time_sorting -> {
                    SORTED_WITH = "TIME"
                    adapter.sorting(SORTED_WITH, ASC_OR_DESC)
                }
                R.id.title_sorting -> {
                    SORTED_WITH = "TITLE"
                    adapter.sorting(SORTED_WITH, ASC_OR_DESC)

                }
                R.id.color_sorting -> {
                    SORTED_WITH = "COLOR"
                    adapter.sorting(SORTED_WITH, ASC_OR_DESC)
                }
            }
        }

        binding.orderSortGroupView.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.ascending -> {
                    ASC_OR_DESC = "ASC"
                    adapter.sorting(SORTED_WITH, ASC_OR_DESC)
                }
                R.id.descending -> {
                    ASC_OR_DESC = "DESC"
                    adapter.sorting(SORTED_WITH, ASC_OR_DESC)
                }

            }
        }

    }
}