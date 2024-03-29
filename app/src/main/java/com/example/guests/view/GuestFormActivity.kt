package com.example.guests.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guests.R
import com.example.guests.constants.GuestConstants
import com.example.guests.databinding.ActivityGuestFormBinding
import com.example.guests.model.GuestModel
import com.example.guests.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel

    //quando é inserção o guestId =0, quando é edição o guestId é diferente de 0
    private var guestId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        binding.buttonSave.setOnClickListener(this)
        binding.radioPresent.isChecked = true

        observe()
        loadData()

    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            //aqui vai ser atualizado o valor, já que o guestID recebe um valor diferente de 0
            guestId = bundle.getInt(GuestConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.button_save) {
                //pensando em passar a informação da activity até o banco de dados:
                val name = binding.editName.text.toString()
                val presence = binding.radioPresent.isChecked
                //na inserção vem o valor padrão = 0
                val model = GuestModel().apply {
                    this.id = guestId
                    this.name = name
                    this.presence = presence
                }
                viewModel.save(model)
            }
        }
    }

    private fun observe() {
        viewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioPresent.isChecked = true
            } else {
                binding.radioAbsent.isChecked = true
            }
        })

        viewModel.saveGuest.observe(this, Observer {
            if (it) {
                if(guestId == 0) {
                    Toast.makeText(applicationContext, "successful insertion", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "successful update", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(applicationContext, "FAIL", Toast.LENGTH_SHORT).show()
            }
        })
    }
}