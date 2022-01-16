import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentAddEditBinding
import com.survivalcoding.noteapp.domain.model.Color
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.presentation.addedit.AddEditViewModel
import com.survivalcoding.noteapp.presentation.addedit.AddEditViewModelFactory
import java.util.*

class AddEditFragment : Fragment() {

    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AddEditViewModel> {
        AddEditViewModelFactory((requireActivity().application as App).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (arguments?.get("noteId") as Int?)?.run { viewModel.getNoteById(this) }

        // 기존 노트 정보 표시
        viewModel.note.observe(this) { note ->
            binding.addEditEtTitle.setText(note.title)
            binding.addEditEtContent.setText(note.content)

            when (note.color) {
                Color.ORANGE.resId() -> viewModel.changeColor(Color.ORANGE)
                Color.YELLOW.resId() -> viewModel.changeColor(Color.YELLOW)
                Color.PURPLE.resId() -> viewModel.changeColor(Color.PURPLE)
                Color.BLUE.resId() -> viewModel.changeColor(Color.BLUE)
                Color.PINK.resId() -> viewModel.changeColor(Color.PINK)
            }
        }

        binding.addEditIvOrange.setOnClickListener { viewModel.changeColor(Color.ORANGE) }
        binding.addEditIvYellow.setOnClickListener { viewModel.changeColor(Color.YELLOW) }
        binding.addEditIvPurple.setOnClickListener { viewModel.changeColor(Color.PURPLE) }
        binding.addEditIvBlue.setOnClickListener { viewModel.changeColor(Color.BLUE) }
        binding.addEditIvPink.setOnClickListener { viewModel.changeColor(Color.PINK) }

        binding.addEditFabSave.setOnClickListener {
            val title = binding.addEditEtTitle.text.trim().toString()
            val content = binding.addEditEtContent.text.trim().toString()
            when {
                title.isEmpty() -> Toast.makeText(
                    context,
                    "The title of the note can't be empty",
                    Toast.LENGTH_SHORT
                ).show()
                content.isEmpty() -> Toast.makeText(
                    context,
                    "The content of the note can't be empty",
                    Toast.LENGTH_SHORT
                ).show()
                else -> {
                    viewModel.insertNote(title, content, Date().time)
                    parentFragmentManager.popBackStack()
                }
            }
        }

        // 배경 색상 변경
        viewModel.color.observe(this) { color -> setBackgroundColor(color) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 배경 색상 및 색상 선택 아이콘 변경
    private fun setBackgroundColor(color: Color) {
        binding.root.setBackgroundColor(resources.getColor(color.resId(), context?.theme))
        binding.addEditIvOrange.setImageDrawable(null)
        binding.addEditIvYellow.setImageDrawable(null)
        binding.addEditIvPurple.setImageDrawable(null)
        binding.addEditIvBlue.setImageDrawable(null)
        binding.addEditIvPink.setImageDrawable(null)

        when (color) {
            Color.ORANGE -> binding.addEditIvOrange.setImageResource(R.drawable.ic_color_border)
            Color.YELLOW -> binding.addEditIvYellow.setImageResource(R.drawable.ic_color_border)
            Color.PURPLE -> binding.addEditIvPurple.setImageResource(R.drawable.ic_color_border)
            Color.BLUE -> binding.addEditIvBlue.setImageResource(R.drawable.ic_color_border)
            Color.PINK -> binding.addEditIvPink.setImageResource(R.drawable.ic_color_border)
        }
    }
}