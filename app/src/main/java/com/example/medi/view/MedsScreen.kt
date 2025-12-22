package com.example.medi.view

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medi.R
import com.example.medi.model.medsModel
import com.example.medi.repository.medsRepoImpl
import com.example.medi.ui.theme.Background
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
            .padding(16.dp)
    ) {
        Row {
            Icon(
                painterResource(R.drawable.pills_icon),
                null,
                modifier = Modifier.padding(end = 10.dp).size(25.dp),
                tint = IconActive
            )
            Text(
                text = "Medications",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "Manage your medication list",
            fontSize = 14.sp,
            color = Color.Gray
        )

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

        // Display medications list
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
                    Text(med.status, fontSize = 12.sp)
                }
            }
        }
    }
}
