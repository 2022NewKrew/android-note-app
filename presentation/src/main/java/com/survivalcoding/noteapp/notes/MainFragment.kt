package com.survivalcoding.noteapp.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NotesViewModel
    private var SORTED_WITH = "TIME"
    private var ASC_OR_DESC = "ASC"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(layoutInflater)
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
            onLeftSwiped = { position, note ->
                viewModel.eventListener(
                    MainEvent.SwipeDeleteEvent(
                        position,
                        note
                    )
                ) // delete temporary
                Snackbar.make(
                    binding.root,
                    "cancel delete",
                    Snackbar.LENGTH_SHORT
                ).setAction("cancel") {
                    viewModel.unDoDelete()
                }.addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        println("$event $DISMISS_EVENT_ACTION")
                        if (event != DISMISS_EVENT_ACTION) {
                            viewModel.deleteNote()
                        }
                    }
                }).show()
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
                replace<EditFragment>(R.id.fragment_container_view)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        // sorting
        binding.contentSortGroupView.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.time_sorting -> SORTED_WITH = "TIME"
                R.id.title_sorting -> SORTED_WITH = "TITLE"
                R.id.color_sorting -> SORTED_WITH = "COLOR"
            }
            viewModel.eventListener(MainEvent.SortingEvent(SORTED_WITH, ASC_OR_DESC))
        }

        binding.orderSortGroupView.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.ascending -> ASC_OR_DESC = "ASC"
                R.id.descending -> ASC_OR_DESC = "DESC"
            }
            viewModel.eventListener(MainEvent.SortingEvent(SORTED_WITH, ASC_OR_DESC))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // memory leak을 까먹지 말자
        _binding = null
    }
}