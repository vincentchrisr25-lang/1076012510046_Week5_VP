package com.example.week5.Soal1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week5.Soal1.model.Mdl

class VM : ViewModel() {
    var name by mutableStateOf("")
    var amount by mutableStateOf("")
    var isError by mutableStateOf(false)
    var list = mutableStateListOf<Mdl>()
    var id = 0

    var editItem by mutableStateOf<Mdl?>(null)
    var editAmount by mutableStateOf("")

    fun add() {
        var temAmount = 0
        try {
            temAmount = amount.toInt()
        } catch (e: Exception) {
            temAmount = 0
        }
        
        if (temAmount <= 0) {
            isError = true
        } else {
            isError = false
            list.add(Mdl(id, name, temAmount))
            id = id + 1
            name = ""
            amount = ""
        }
    }

    fun del(data: Mdl) {
        list.remove(data)
    }

    fun openEdit(data: Mdl) {
        editItem = data
        editAmount = data.amount.toString()
    }

    fun saveEdit() {
        var parsedAmount = 0
        try {
            parsedAmount = editAmount.toInt()
        } catch (e: Exception) {
            parsedAmount = 0
        }

        if (parsedAmount > 0) {
            val i = list.indexOf(editItem)
            if (i != -1) {
                list[i] = Mdl(editItem!!.id, editItem!!.name, parsedAmount)
            }
            editItem = null
        }
    }

    fun cancelEdit() {
        editItem = null
    }

    fun total(): Int {
        var total = 0
        for (item in list) {
            total = total + item.amount
        }
        return total
    }
}