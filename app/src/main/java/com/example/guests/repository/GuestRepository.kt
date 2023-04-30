package com.example.guests.repository

import android.content.Context
import com.example.guests.model.GuestModel

class GuestRepository (context: Context) {

    // Acesso ao banco de dados
    private val dataBase = GuestDataBase.getDataBase(context).guestDAO()

    /**
     * Carrega convidado
     */
    fun get(id: Int): GuestModel {
        return dataBase.load(id)
    }

    /**
     * Insere convidado
     */
    fun insert(guest: GuestModel): Boolean {
        return dataBase.insert(guest) > 0
    }

    /**
     * Faz a listagem de todos os convidados
     */
    fun getAll(): List<GuestModel> {
        return dataBase.getAll()
    }

    /**
     * Faz a listagem de todos os convidados presentes
     */
    fun getPresent(): List<GuestModel> {
        return dataBase.getPresent()
    }

    /**
     * Faz a listagem de todos os convidados presentes
     */
    fun getAbsent(): List<GuestModel> {
        return dataBase.getAbsent()
    }

    /**
     * Atualiza convidado
     */
    fun update(guest: GuestModel): Boolean {
        return dataBase.update(guest) > 0
    }

    /**
     * Remove convidado
     */
    fun delete(guest: GuestModel) {
        dataBase.delete(guest)
    }
}