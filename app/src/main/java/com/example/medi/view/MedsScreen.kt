package com.example.medi.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medi.R
import com.example.medi.model.medsModel
import com.example.medi.repository.medsRepoImpl
import com.example.medi.ui.theme.Background
import com.example.medi.ui.theme.Card
import com.example.medi.ui.theme.DarkTextColor
import com.example.medi.ui.theme.IconActive
import com.example.medi.ui.theme.TextColor
import com.example.medi.viewModel.MedsViewModel

@Composable
fun MedsScreen() {
    val medsViewModel = remember { MedsViewModel(medsRepoImpl()) }


    val allmeds = medsViewModel.allmeds.observeAsState(initial = emptyList())


    LaunchedEffect(Unit) {
        medsViewModel.getAllmeds()
    }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddMedicationDialog(
            viewModel = medsViewModel,
            onDismiss = { showDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.pills_icon),
                        contentDescription = null,
                        tint = IconActive,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Medication",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkTextColor
                    )
                }

                Text(
                    text = "Manage your medicine",
                    fontSize = 13.sp,
                    color = TextColor
                )
            }

            Button(
                onClick = { showDialog = true },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Card)
            ) {
                Text("+ Add")
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search medications…") },
            leadingIcon = {
                Icon(painter = painterResource(R.drawable.baseline_search_24), contentDescription = "Search")
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.LightGray
            )
        )

        Spacer(Modifier.height(16.dp))

        if (allmeds.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No medications found", color = Color.Gray)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = allmeds.value) { med ->
                    MedicationCard(med = med)
                }
            }
        }
    }
}

@Composable
fun MedicationCard(med: medsModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(Color(0xFFE6F4F1), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painterResource(R.drawable.bottompill),
                            modifier = Modifier.size(25.dp),
                            tint = IconActive,
                            contentDescription = null
                        )
                    }

                    Spacer(Modifier.width(12.dp))

                    Column {
                        Text(med.name, fontWeight = FontWeight.Bold)
                        Text(med.dosage, color = TextColor)
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // TIME
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.baseline_access_time_24),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("${med.schedule} • ${med.frequency}", color = TextColor)
            }

            Spacer(Modifier.height(10.dp))
            HorizontalDivider()
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = med.notes,
                    fontStyle = FontStyle.Italic,
                    color = TextColor
                )

                Box(
                    modifier = Modifier
                        .background(
                            if (med.status == "Taken") Color(0xFFDFF5EA)
                            else Color(0xFFEAF3F7),
                            RoundedCornerShape(50)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(if(med.status.isNotEmpty()) med.status else "Pending", fontSize = 12.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationDialog(
    viewModel: MedsViewModel,
    onDismiss: () -> Unit
) {
    var medName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("08:00 AM") }
    var frequency by remember { mutableStateOf("Daily") }
    var notes by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Daily", "Twice", "Thrice", "Weekly", "As needed")

    val context = LocalContext.current
    val fieldShape = RoundedCornerShape(12.dp)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add New Medication",
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                // Medication name
                OutlinedTextField(
                    value = medName,
                    onValueChange = { medName = it },
                    label = { Text("Medication Name") },
                    placeholder = { Text("e.g., Vitamin D3") },
                    singleLine = true,
                    shape = fieldShape,
                    modifier = Modifier.fillMaxWidth()
                )

                // Dosage + Time
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                    OutlinedTextField(
                        value = dosage,
                        onValueChange = { dosage = it },
                        label = { Text("Dosage") },
                        placeholder = { Text("e.g., 500mg") },
                        singleLine = true,
                        shape = fieldShape,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it },
                        label = { Text("Time") },
                        singleLine = true,
                        shape = fieldShape,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Frequency dropdown
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = frequency,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Frequency") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                        },
                        shape = fieldShape,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    frequency = it
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Notes
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (optional)") },
                    placeholder = { Text("e.g., Take with food") },
                    shape = fieldShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                )
            }
        },
        confirmButton = {
            Button(

                colors = ButtonDefaults.buttonColors(containerColor = Card),
                onClick = {
                    val newMeds = medsModel(
                        name = medName,
                        dosage = dosage,
                        schedule = time,
                        frequency = frequency,
                        notes = notes,
                        status = "Pending"
                    )

                    if (medName.isBlank() || dosage.isBlank() || time.isBlank()) {
                        Toast
                            .makeText(context, "Please fill required fields", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        viewModel.addMeds(newMeds) { success, message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            if (success) {
                                viewModel.getAllmeds()
                                onDismiss()
                            }
                        }
                    }
                }

                        ,

            ) {
                Text("Add Medication")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
