package com.example.week5.Soal1.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week5.Soal1.viewmodel.VM

fun formatUang(angka: Int): String {
    val teks = angka.toString()
    var hasil = ""
    var hitung = 0
    
    var i = teks.length - 1
    while (i >= 0) {
        hasil = teks[i] + hasil
        hitung++
        if (hitung == 3 && i != 0) {
            hasil = "." + hasil
            hitung = 0
        }
        i--
    }
    
    return hasil
}

@Composable
fun View(viewModel: VM = viewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 32.dp)
        ) {
            Text(
                text = "Expenses",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Total Expense : Rp " + formatUang(viewModel.total()),
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text("Expense Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = viewModel.amount,
                onValueChange = { viewModel.amount = it },
                label = { Text("Amount of Expense") },
                isError = viewModel.isError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                supportingText = {
                    if (viewModel.isError) {
                        Text("Amount must be greater than 0", color = Color.Red)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
                    .clickable { viewModel.add() }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }

        LazyColumn {
            items(viewModel.list) { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFE8EAF6))
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Name : " + item.name,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF546E7A),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Rp " + formatUang(item.amount),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color(0xFFD4AF37),
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { viewModel.openEdit(item) }
                    )
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.clickable { viewModel.del(item) }
                    )
                }
            }
        }
    }

    if (viewModel.editItem != null) {
        AlertDialog(
            onDismissRequest = { viewModel.cancelEdit() },
            title = { Text(text = "Edit Expense") },
            text = {
                Column {
                    Text(text = "Name : " + viewModel.editItem!!.name, modifier = Modifier.padding(bottom = 8.dp))
                    OutlinedTextField(
                        value = viewModel.editAmount,
                        onValueChange = { viewModel.editAmount = it },
                        label = { Text("New Amount") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.saveEdit() }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.cancelEdit() }) {
                    Text("Cancel")
                }
            }
        )
    }
    }
}