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

@Composable
fun AppointmentScreen() {
    val appointmentViewModel = remember { AppointmentViewModel(appointmentRepoImpl()) }
    val allAppointments = appointmentViewModel.appointments.observeAsState(initial = emptyList())


    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddAppointmentDialog(
            onDismiss = { showDialog = false },
            onAdd = { newAppointment ->
                showDialog = false

            }
        )
    }

    LaunchedEffect(Unit) {
        appointmentViewModel.getAllAppointments()
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

                onClick = { showDialog = true },
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
        Spacer(modifier = Modifier.height(8.dp))

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

                    AppointmentCard(appoint)
                }
            }
        }
    }
}

@Composable
fun AddAppointmentDialog(
    onDismiss: () -> Unit,
    onAdd: (appointmentModel) -> Unit
) {
    val context = LocalContext.current

    val appointmentViewModel = remember { AppointmentViewModel(appointmentRepoImpl()) }

    var docName by remember { mutableStateOf("") }
    var speciality by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        containerColor = Color.White,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add New Appointment",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                Text("Doctor Name", fontSize = 12.sp)
                TextField(
                    value = docName,
                    onValueChange = { docName = it },
                    placeholder = {
                        Text(
                            "e.g. Dr. Sarah Johnson",
                            fontSize = 13.sp
                        )
                    },
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
                    modifier = Modifier.fillMaxWidth().height(36.dp),
                    shape = RoundedCornerShape(14.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Date", fontSize = 12.sp)
                        OutlinedTextField(
                            value = date,
                            onValueChange = { date = it },
                            placeholder = { Text("mm / dd / yyyy", fontSize = 12.sp) },
                            singleLine = true,
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.calender_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            modifier = Modifier.fillMaxWidth().height(36.dp),
                            shape = RoundedCornerShape(14.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Time", fontSize = 12.sp)
                        OutlinedTextField(
                            value = time,
                            onValueChange = { time = it },
                            placeholder = { Text("09:00 AM", fontSize = 12.sp) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().height(36.dp),
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
                    modifier = Modifier.fillMaxWidth().height(36.dp),
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
                    val newAppointment = appointmentModel(
                        id = "",
                        doctorName = docName,
                        specialty = speciality,
                        date = date,
                        time = time,
                        location = location,
                        notes = notes
                    )

                    if (newAppointment.doctorName.isEmpty() || newAppointment.date.isEmpty() || newAppointment.time.isEmpty()) {
                        Toast.makeText(context, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                 appointmentViewModel.addAppointment(newAppointment){
                     success,message->{
                         if (success) {
                             Log.d("success",message)

                             Toast.makeText(context, "Appointment Added Successfully", Toast.LENGTH_SHORT).show()

                         }
                        else{
                             Toast.makeText(context, "Appointment Failed To Add", Toast.LENGTH_SHORT).show()
                            Log.d("error",message)

                         }

                 }
                 }
                },
                modifier = Modifier
                    .height(44.dp)
                    .width(140.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Card),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Add")
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
