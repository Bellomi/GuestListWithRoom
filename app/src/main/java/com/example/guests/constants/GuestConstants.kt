package com.example.guests.constants

class GuestConstants private constructor() {
    //pra nao correr o risco de colocar o nome errado nas informações da tabela
    object GUEST {
        const val ID = "guestID"
    }

    object FILTER {
        const val EMPTY = 0
        const val PRESENT = 1
        const val ABSENT = 2
    }
}
