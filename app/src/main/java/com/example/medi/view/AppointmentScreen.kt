package com.example.medi.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medi.R
import com.example.medi.model.appointmentModel
import com.example.medi.repository.appointmentRepoImpl
import com.example.medi.ui.theme.Background
import com.example.medi.ui.theme.BorderOutline
import com.example.medi.ui.theme.Card
import com.example.medi.ui.theme.DarkTextColor
import com.example.medi.ui.theme.IconActive
import com.example.medi.ui.theme.TextColor
import com.example.medi.viewModel.AppointmentViewModel
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Calendar


@Composable
fun AppointmentScreen() {
    val context = LocalContext.current
    val appointmentViewModel = remember { AppointmentViewModel(appointmentRepoImpl()) }
    val allAppointments = appointmentViewModel.appointments.observeAsState(initial = emptyList())


    var showDialog by remember { mutableStateOf(false) }
    var selectedAppointment by remember { mutableStateOf<appointmentModel?>(null) }

    LaunchedEffect(Unit) {
        appointmentViewModel.getAllAppointments()
    }

    // Open Dialog
    if (showDialog) {
        AppointmentOperationDialog(
            appointmentToEdit = selectedAppointment,
            onDismiss = {
                showDialog = false
                selectedAppointment = null
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
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
                        painter = painterResource(R.drawable.calender_icon),
                        contentDescription = null,
                        tint = IconActive,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Appointments",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkTextColor
                    )
                }

                Text(
                    text = "Manage your doctor visits",
                    fontSize = 13.sp,
                    color = TextColor
                )
            }

            Button(
                onClick = {
                    selectedAppointment = null
                    showDialog = true
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Card)
            ) {
                Text("+ Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search appointments...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = BorderOutline,
                focusedBorderColor = IconActive
            )
        )

        Spacer(modifier = Modifier.height(12.dp))


        if (allAppointments.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No Upcoming Appointment", color = Color.Gray)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = allAppointments.value) { appoint ->
                    AppointmentCard(
                        appointment = appoint,
                        onEdit = {
                            selectedAppointment = appoint
                            showDialog = true
                        },
                        onDelete = {
                            appointmentViewModel.deleteAppointment(appoint.id) { success, msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                if (success) {
                                    appointmentViewModel.getAllAppointments()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentOperationDialog(
    appointmentToEdit: appointmentModel? = null,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    val appointmentViewModel = remember { AppointmentViewModel(appointmentRepoImpl()) }


    var docName by remember { mutableStateOf(appointmentToEdit?.doctorName ?: "") }
    var speciality by remember { mutableStateOf(appointmentToEdit?.specialty ?: "") }
    var date by remember { mutableStateOf(appointmentToEdit?.date ?: "") }
    var time by remember { mutableStateOf(appointmentToEdit?.time ?: "") }
    var location by remember { mutableStateOf(appointmentToEdit?.location ?: "") }
    var notes by remember { mutableStateOf(appointmentToEdit?.notes ?: "") }

    // Date/Time Pickers State
    var openDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var openTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    // --- Date Picker Logic ---
    if (openDatePicker) {
        DatePickerDialog(
            onDismissRequest = { openDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                            date = formatter.format(Date(millis))
                        }
                        openDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { openDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }


    if (openTimePicker) {
        AlertDialog(
            onDismissRequest = { openTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val cal = Calendar.getInstance()
                        cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        cal.set(Calendar.MINUTE, timePickerState.minute)
                        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
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
                text = if (appointmentToEdit == null) "Add New Appointment" else "Edit Appointment",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                Text("Doctor Name", fontSize = 12.sp)
                TextField(
                    value = docName,
                    onValueChange = { docName = it },
                    placeholder = { Text("e.g. Dr. Sarah Johnson", fontSize = 13.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Gray,
                        unfocusedIndicatorColor = Color.LightGray
                    )
                )

                Text("Specialty (optional)", fontSize = 12.sp)
                OutlinedTextField(
                    value = speciality,
                    onValueChange = { speciality = it },
                    placeholder = { Text("e.g. Cardiologist", fontSize = 12.sp) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(14.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Date Field
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Date", fontSize = 12.sp)
                        OutlinedTextField(
                            value = date,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text("mm/dd/yyyy", fontSize = 12.sp) },
                            singleLine = true,
                            trailingIcon = {
                                IconButton(onClick = { openDatePicker = true }) {
                                    Icon(
                                        painter = painterResource(R.drawable.calender_icon),
                                        contentDescription = "Select Date",
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(14.dp)
                        )
                    }

                    // Time Field
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Time", fontSize = 12.sp)
                        OutlinedTextField(
                            value = time,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text("09:00 AM", fontSize = 12.sp) },
                            singleLine = true,
                            trailingIcon = {
                                IconButton(onClick = { openTimePicker = true }) {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_access_time_24),
                                        contentDescription = "Select Time",
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(14.dp)
                        )
                    }
                }

                Text("Location (optional)", fontSize = 12.sp)
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    placeholder = { Text("e.g. City Medical Center", fontSize = 12.sp) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(14.dp)
                )

                Text("Notes (optional)", fontSize = 12.sp)
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = { Text("e.g. Bring recent lab results", fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    shape = RoundedCornerShape(14.dp),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (docName.isBlank() || date.isBlank() || time.isBlank()) {
                        Toast.makeText(context, "Please fill required fields", Toast.LENGTH_SHORT).show()
                    } else {
                        if (appointmentToEdit == null) {

                            val newAppointment = appointmentModel(
                                id = "",
                                doctorName = docName,
                                specialty = speciality,
                                date = date,
                                time = time,
                                location = location,
                                notes = notes
                            )
                            appointmentViewModel.addAppointment(newAppointment) { success, message ->
                                if (success) {
                                    Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show()
                                    onDismiss()
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {

                            val updatedAppointment = appointmentToEdit.copy(
                                doctorName = docName,
                                specialty = speciality,
                                date = date,
                                time = time,
                                location = location,
                                notes = notes
                            )
                            appointmentViewModel.updateAppointment(appointmentToEdit.id, updatedAppointment) { success, message ->
                                if (success) {
                                    Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()
                                    onDismiss()
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.height(44.dp).width(140.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Card),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(if (appointmentToEdit == null) "Add" else "Update")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.height(44.dp).width(110.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Cancel")
            }
        }
    )
}