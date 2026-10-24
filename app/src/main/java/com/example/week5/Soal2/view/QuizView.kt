package com.example.week5.Soal2.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week5.Soal2.viewmodel.QuizVM

@Composable
fun QuizView(vm: QuizVM = viewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (!vm.isMulai) {
            ViewStart(vm = vm)
        } else {
            if (vm.selesai) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .padding(top = 64.dp)
                ) {
                    Text(
                        text = "Quiz Finished!",
                        fontSize = 28.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Your Score: " + vm.skor + " / " + vm.totalDiminta,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        textAlign = TextAlign.Center
                    )

                    if (vm.salah.isEmpty()) {
                        Text(
                            text = "Perfect! No wrong answers.",
                            color = Color(0xFF3F51B5),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(vm.salah) { item ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp)
                                ) {
                                    Text(
                                        text = "Q" + (vm.soalAktif.indexOf(item.soal) + 1) + ": " + item.soal.teks,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    Text(
                                        text = "Your Answer: " + item.soal.opsi[item.jawabUser],
                                        color = Color.Red,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(bottom = 2.dp)
                                    )
                                    Text(
                                        text = "Correct Answer: " + item.soal.opsi[item.soal.benar],
                                        color = Color(0xFF3F51B5),
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp)
                                            .background(Color.LightGray)
                                            .padding(0.5.dp)
                                    )
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFF4A5582))
                            .clickable { vm.restart() }
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Restart Quiz",
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .padding(top = 64.dp)
            ) {
                Text(
                    text = "Question " + (vm.nomor + 1),
                    fontSize = 20.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = vm.soalAktif[vm.nomor].teks,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 48.dp),
                    textAlign = TextAlign.Center
                )

                vm.soalAktif[vm.nomor].opsi.forEachIndexed { i, opsi ->
                    val bg = if (vm.pilih == i) Color(0xFF4CAF50) else Color(0xFFE8EAF6)
                    val tc = if (vm.pilih == i) Color.White else Color(0xFF4A5582)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(bg)
                            .clickable { vm.jawab(i) }
                            .padding(16.dp)
                    ) {
                        Text(
                            text = opsi,
                            color = tc,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(if (vm.pilih != -1) Color(0xFF4A5582) else Color(0xFFE0E0E0))
                        .clickable(enabled = vm.pilih != -1) { vm.lanjut() }
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Next",
                        color = if (vm.pilih != -1) Color.White else Color.Gray,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            }
        }
    }
}