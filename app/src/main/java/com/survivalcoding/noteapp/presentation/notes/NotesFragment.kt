import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNotesBinding
import com.survivalcoding.noteapp.presentation.notes.NotesViewModel
import com.survivalcoding.noteapp.presentation.notes.NotesViewModelFactory
import com.survivalcoding.noteapp.presentation.notes.adapter.NoteListAdapter

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NotesViewModel> {
        NotesViewModelFactory((requireActivity().application as App).repository)
    }
    private val adapter by lazy {
        NoteListAdapter({ todo -> viewModel.deleteNote(todo) }, { todo ->
            moveToAdd()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // recyclerView 설정
        binding.notesRecyclerView.adapter = adapter

        // 작성하기 화면으로 이동
        binding.notesFabAdd.setOnClickListener { moveToAdd() }

        // note list 업데이트 관찰
        viewModel.notes.observe(this) { list ->
            adapter.submitList(list)
        }

        // 할일 목록 조회
        viewModel.getNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun moveToAdd() {
        parentFragmentManager.commit {
            replace<AddEditFragment>(R.id.main_fragment_container_view)
            setReorderingAllowed(true)
            addToBackStack(null) // name can be null
        }
    }
}