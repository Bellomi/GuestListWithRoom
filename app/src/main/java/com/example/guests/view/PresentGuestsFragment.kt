package com.example.guests.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guests.constants.GuestConstants
import com.example.guests.databinding.FragmentPresentGuestsBinding
import com.example.guests.view.adapter.GuestsAdapter
import com.example.guests.view.listener.OnGuestListener
import com.example.guests.viewmodel.GuestsViewModel

class PresentGuestsFragment : Fragment() {
    private var _binding: FragmentPresentGuestsBinding? = null
    private val binding get() = _binding!!
    private val adapter = GuestsAdapter()
    private lateinit var viewModel: GuestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[GuestsViewModel::class.java]

        _binding = FragmentPresentGuestsBinding.inflate(inflater, container, false)
        //Layout
        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)
        //Adapter
        binding.recyclerGuests.adapter = adapter
        val listener = object : OnGuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)
                //pra diferenciar o que faz o botão salvar -> insere ou edita convidado ja existente
                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUEST.ID, id)
                intent.putExtras(bundle)
                startActivity(intent)

            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.load(GuestConstants.FILTER.PRESENT)
            }
        }

        adapter.attachListener(listener)

        observe()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.load(GuestConstants.FILTER.PRESENT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.guests.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)
        }
    }
}