package com.example.guests.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guests.constants.GuestConstants
import com.example.guests.model.GuestModel
import com.example.guests.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GuestRepository(application.applicationContext)
    private val listAllGuests = MutableLiveData<List<GuestModel>>()
    val guests: LiveData<List<GuestModel>> = listAllGuests

    fun load(filter: Int) {
        if (filter == GuestConstants.FILTER.EMPTY) {
            listAllGuests.value = repository.getAll()
        } else if (filter == GuestConstants.FILTER.PRESENT) {
            listAllGuests.value = repository.getPresent()
        } else {
            listAllGuests.value = repository.getAbsent()
        }
    }

    fun delete(id: Int) {
        repository.delete(repository.get(id))
    }
}
