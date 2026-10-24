package com.example.week5.Soal2.model

data class SoalModel(
    val teks: String,
    val opsi: List<String>,
    val benar: Int
)

data class JawabModel(
    val soal: SoalModel,
    val jawabUser: Int
)