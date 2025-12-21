package com.example.medi.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medi.R
import com.example.medi.ui.theme.AquaIcon
import com.example.medi.ui.theme.Background
import com.example.medi.ui.theme.BorderOutline
import com.example.medi.ui.theme.Card
import com.example.medi.ui.theme.DarkTextColor
import com.example.medi.ui.theme.IconActive
import com.example.medi.ui.theme.TextColor


@Composable
fun AppointmentScreen() {

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
                    color =
                        TextColor
                )
            }

            Button(
                onClick = {},
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


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                AppointmentCard(
                    doctorName = "Dr. Ram Bhandari",
                    specialty = "General Physician",
                    date = "Wednesday, Dec 19, 2025",
                    time = "10:00",
                    location = "City Center",
                    note = "Annual checkup"
                )
            }

            item {
                AppointmentCard(
                    doctorName = "Dr.  Chen",
                    specialty = "Cardiologist",
                    date = "Sunday, Dec 14, 2025",
                    time = "14:30",
                    location = "Heart Care Clinic",
                    note = "Follow-up visit"
                )
            }
        }
    }
}



@Composable
fun AppointmentCard(
    doctorName: String,
    specialty: String,
    date: String,
    time: String,
    location: String,
    note: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Background),
        border = BorderStroke(1.dp, BorderOutline)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            // Top row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(AquaIcon, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.profile),
                            contentDescription = null,
                            tint = IconActive
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = doctorName,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkTextColor
                        )
                        Text(
                            text = specialty,
                            fontSize = 13.sp,
                            color = TextColor
                        )
                    }
                }

                Icon(
                    painter = painterResource(R.drawable.outline_more_vert_24),
                    contentDescription = null,
                    tint = IconActive,
                    modifier = Modifier.size(25.dp)

                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.calender_icon
                    ),
                    contentDescription = null,
                    tint = IconActive,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(date, fontSize = 13.sp, color = TextColor)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.baseline_access_time_24),
                    contentDescription = null,
                    tint = IconActive,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(time, fontSize = 13.sp, color = TextColor)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.location
                    ),
                    contentDescription = null,
                    tint = IconActive,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(location, fontSize = 13.sp, color = TextColor)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = note,
                fontSize = 13.sp,
                color = TextColor
            )
        }
    }
}
