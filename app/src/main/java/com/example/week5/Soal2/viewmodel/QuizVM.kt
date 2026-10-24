package com.example.week5.Soal2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week5.Soal2.model.JawabModel
import com.example.week5.Soal2.model.SoalModel

class QuizVM : ViewModel() {
    val semuaSoal = listOf(
        SoalModel("Negara dengan wilayah terluas di dunia?", listOf("China", "Rusia", "Amerika Serikat", "Kanada"), 1),
        SoalModel("Sungai terpanjang di dunia adalah?", listOf("Nil", "Amazon", "Yangtze", "Mississippi"), 0),
        SoalModel("Benua terkecil di dunia?", listOf("Eropa", "Antartika", "Australia", "Amerika Selatan"), 2),
        SoalModel("Mata uang negara Jepang?", listOf("Yuan", "Won", "Yen", "Rupee"), 2),
        SoalModel("Gunung tertinggi di dunia?", listOf("K2", "Kangchenjunga", "Makalu", "Everest"), 3),
        SoalModel("Samudra terbesar di bumi?", listOf("Hindia", "Atlantik", "Pasifik", "Arktik"), 2),
        SoalModel("Negara berpenduduk terbanyak di dunia (2024)?", listOf("India", "China", "Amerika Serikat", "Indonesia"), 0),
        SoalModel("Planet terdekat dengan matahari?", listOf("Venus", "Merkurius", "Bumi", "Mars"), 1),
        SoalModel("Hewan mamalia terbesar di bumi?", listOf("Gajah Afrika", "Hiu Paus", "Paus Biru", "Jerapah"), 2),
        SoalModel("Menara Eiffel terletak di kota?", listOf("London", "Roma", "Berlin", "Paris"), 3)
    )

    var soalAktif by mutableStateOf<List<SoalModel>>(emptyList())
    var nomor by mutableStateOf(0)
    var pilih by mutableStateOf(-1)
    var selesai by mutableStateOf(false)
    val salah = mutableStateListOf<JawabModel>()
    var skor by mutableStateOf(0)

    var isMulai by mutableStateOf(false)
    var inputJumlah by mutableStateOf("")
    var isErrorInput by mutableStateOf(false)
    var totalDiminta by mutableStateOf(0)

    fun validasiDanMulai() {
        var jumlah = 0
        try {
            jumlah = inputJumlah.toInt()
        } catch (e: Exception) {
            jumlah = 0
        }

        if (jumlah > 0 && jumlah <= semuaSoal.size) {
            isErrorInput = false
            totalDiminta = jumlah
            soalAktif = semuaSoal.shuffled().take(jumlah)
            nomor = 0
            pilih = -1
            selesai = false
            salah.clear()
            skor = 0
            isMulai = true
        } else {
            isErrorInput = true
        }
    }

    fun restart() {
        isMulai = false
        inputJumlah = ""
        isErrorInput = false
    }

    fun jawab(idx: Int) {
        pilih = idx
    }

    fun lanjut() {
        if (pilih != -1) {
            val soalSkrg = soalAktif[nomor]
            if (pilih == soalSkrg.benar) {
                skor = skor + 1
            } else {
                salah.add(JawabModel(soalSkrg, pilih))
            }
            
            if (nomor < totalDiminta - 1) {
                nomor = nomor + 1
                pilih = -1
            } else {
                selesai = true
            }
        }
    }
}