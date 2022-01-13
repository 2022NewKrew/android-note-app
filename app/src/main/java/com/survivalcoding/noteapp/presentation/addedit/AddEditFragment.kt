import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.survivalcoding.noteapp.App
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
                    viewModel.insertNote(
                        Note(
                            null,
                            title,
                            content,
                            Date().time,
                            Color.ORANGE.resId()
                        )
                    )
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}