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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
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
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        medsViewModel.getAllmeds()
    }

    var showDialog by remember { mutableStateOf(false) }
    var selectedMed by remember { mutableStateOf<medsModel?>(null) }


    if (showDialog) {
        MedicationOperationDialog(
            medToEdit = selectedMed,
            viewModel = medsViewModel,
            onDismiss = {
                showDialog = false
                selectedMed = null 
            }
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
                onClick = {
                    selectedMed = null 
                    showDialog = true
                },
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
                focusedBorderColor = IconActive
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
                    MedicationCard(
                        med = med,
                        onEdit = {
                            selectedMed = med 
                            showDialog = true
                        },
                        onDelete = {
                            medsViewModel.deleteMeds(med.id) { success, message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                if (success) medsViewModel.getAllmeds()
                            }
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun MedicationCard(
    med: medsModel,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically 
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f) 
                ) {
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

                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_more_vert_24),
                            contentDescription = "Options",
                            tint = Color.Gray
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = {
                                showMenu = false
                                onEdit()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete", color = Color.Red) },
                            onClick = {
                                showMenu = false
                                onDelete()
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

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
                    text = if(med.notes.isNotEmpty()) med.notes else "No notes",
                    fontStyle = FontStyle.Italic,
                    color = TextColor,
                    modifier = Modifier.weight(1f)
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
                    Text(if (med.status.isNotEmpty()) med.status else "Pending", fontSize = 12.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationOperationDialog(
    medToEdit: medsModel? = null, 
    viewModel: MedsViewModel,
    onDismiss: () -> Unit
) {
    var medName by remember { mutableStateOf(medToEdit?.name ?: "") }
    var dosage by remember { mutableStateOf(medToEdit?.dosage ?: "") }
    var time by remember { mutableStateOf(medToEdit?.schedule ?: "08:00 AM") }
    var frequency by remember { mutableStateOf(medToEdit?.frequency ?: "Daily") }
    var notes by remember { mutableStateOf(medToEdit?.notes ?: "") }

    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Daily", "Twice", "Thrice", "Weekly", "As needed")

    val context = LocalContext.current
    val fieldShape = RoundedCornerShape(14.dp)

    var openTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    if (openTimePicker) {
        AlertDialog(
            onDismissRequest = { openTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val cal = java.util.Calendar.getInstance()
                        cal.set(java.util.Calendar.HOUR_OF_DAY, timePickerState.hour)
                        cal.set(java.util.Calendar.MINUTE, timePickerState.minute)
                        val formatter = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
                        time = formatter.format(cal.time)
                        openTimePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { openTimePicker = false }) { Text("Cancel") }
            },
            text = { TimePicker(state = timePickerState) }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        containerColor = Color.White,
        title = {
            Text(
                text = if (medToEdit == null) "Add New Medication" else "Edit Medication",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = medName,
                    onValueChange = { medName = it },
                    label = { Text("Medication Name") },
                    placeholder = { Text("e.g., Vitamin D3") },
                    singleLine = true,
                    shape = fieldShape,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IconActive,
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                    OutlinedTextField(
                        value = dosage,
                        onValueChange = { dosage = it },
                        label = { Text("Dosage") },
                        placeholder = { Text("e.g., 500mg") },
                        singleLine = true,
                        shape = fieldShape,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IconActive,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    OutlinedTextField(
                        value = time,
                        onValueChange = { },
                        readOnly = true, 
                        label = { Text("Time") },
                        singleLine = true,
                        shape = fieldShape,
                        modifier = Modifier.weight(1f),
                        trailingIcon = {
                            IconButton(onClick = { openTimePicker = true }) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_access_time_24),
                                    contentDescription = "Select Time",
                                    tint = IconActive,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IconActive,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )
                }

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
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IconActive,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    frequency = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (optional)") },
                    placeholder = { Text("e.g., Take with food") },
                    shape = fieldShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IconActive,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
            }
        },
        confirmButton = {
            Button(
                modifier = Modifier
                    .height(44.dp)
                    .width(140.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Card),
                onClick = {
                    if (medName.isBlank() || dosage.isBlank() || time.isBlank()) {
                        Toast.makeText(context, "Please fill required fields", Toast.LENGTH_SHORT).show()
                    } else {
                        if (medToEdit == null) {
                            val currentDayOfWeek = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK)

                            val newMeds = medsModel(
                                id = "",
                                name = medName,
                                dosage = dosage,
                                schedule = time,
                                frequency = frequency,
                                notes = notes,
                                status = "Pending"
                                
                            )
                            viewModel.addMeds(newMeds) { success, message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                if (success) {
                                    viewModel.getAllmeds()
                                    onDismiss()
                                }
                            }
                        } else {
                            val updatedMed = medToEdit.copy(
                                name = medName,
                                dosage = dosage,
                                schedule = time,
                                frequency = frequency,
                                notes = notes
                            )
                            viewModel.updateMeds(medToEdit.id, updatedMed) { success, message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                if (success) {
                                    viewModel.getAllmeds()
                                    onDismiss()
                                }
                            }
                        }
                    }
                }
            ) {
                Text(if (medToEdit == null) "Add" else "Update")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier
                    .height(44.dp)
                    .width(110.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Cancel")
            }
        }
    )
}
