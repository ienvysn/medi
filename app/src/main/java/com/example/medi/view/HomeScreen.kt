package com.example.medi.view



import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.medi.R
import com.example.medi.ui.theme.AppoinementBackground
import com.example.medi.ui.theme.AquaIcon
import com.example.medi.ui.theme.Background

import com.example.medi.ui.theme.BorderOutline
import com.example.medi.ui.theme.Card
import com.example.medi.ui.theme.ChipBackground
import com.example.medi.ui.theme.DarkTextColor
import com.example.medi.ui.theme.IconActive
import com.example.medi.ui.theme.TextColor


@Composable
fun HomeScreen(){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(color = Background)

    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Card),

            ){
                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 10.dp)) {
                    Text("Good Afternoon",style = TextStyle(fontSize = 15.sp), color = Color.White)
                    Spacer(Modifier.height(10.dp))
                    Text("Lavnik!", style = TextStyle(fontSize = 25.sp,fontWeight = FontWeight.Bold), color = Color.White)
                    Spacer(Modifier.height(10.dp))

                }


            }
        }
        item {
            Spacer(Modifier.height(10.dp))
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .background(color = Color.White,shape = RoundedCornerShape(8.dp))
                    .border(1.dp, BorderOutline,shape = RoundedCornerShape(8.dp))


                           ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp, horizontal = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(modifier = Modifier.fillMaxWidth().padding(all=10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(R.drawable.pills_icon), contentDescription = null, modifier = Modifier.padding(end = 12.dp).size(20.dp),tint = IconActive)

                        Text("Today's Medications",style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp))

                        Spacer(modifier = Modifier.weight(1f))
                        Text("1/3 taken", style = TextStyle(fontSize = 15.sp), color = TextColor)
                    }

                    MedCard()
                    MedCard()
                    MedCard()
                }
            }
        }
//        item {
//            Divider(
//                color = Color.LightGray,
//                thickness = 1.dp,
//                modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
//            )
//        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .background(color = Color.White,shape = RoundedCornerShape(8.dp))
                    .border(1.dp, BorderOutline,shape = RoundedCornerShape(8.dp))


            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp, horizontal = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(modifier = Modifier.fillMaxWidth().padding(all=10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(R.drawable.calender_icon), contentDescription = null, modifier = Modifier.padding(end = 12.dp).size(20.dp),tint = IconActive)

                        Text("Next Appointment ",style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp))

                        Spacer(modifier = Modifier.weight(1f))
                        Text("View All", style = TextStyle(fontSize = 15.sp), color = TextColor)
                    }

                    AppointmentCard()
                    AppointmentCard()
                }
            }
        }
    }
}
@Composable
fun MedCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Vitamin D3",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .background(
                                color = ChipBackground,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            "1 Tablet",
                            fontSize = 12.sp,
                            color = DarkTextColor
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_access_time_24),
                        contentDescription = null,
                        tint = TextColor
                    )
                    Spacer(Modifier.width(6.dp))
                    Text("08:00", color = TextColor, fontSize = 16.sp)

                    Spacer(Modifier.width(8.dp))

                    Text(
                        "Take with breakfast",
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        color = TextColor
                    )
                }
            }

            // (Circle Buttons)
            Row {

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .border(
                            1.dp,
                            Color.LightGray,

                            CircleShape
                        )
                        .clickable { }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.check),

                        contentDescription = null,
                        tint = Color.DarkGray
                    )
                }

                Spacer(Modifier.width(8.dp))


                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .border(
                            1.dp,
                            Color.LightGray,
                            CircleShape
                        )
                        .clickable { }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.cross),
                        contentDescription = null,
                        tint = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
fun AppointmentCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, BorderOutline),
        colors = CardDefaults.cardColors(
            containerColor = AppoinementBackground

        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {


                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(
                            color = AquaIcon,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = null,
                        tint = IconActive,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Dr. Michael Chen",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color =  Color.Black
                    )
                    Text(
                        text = "Cardiologist",
                        fontSize = 14.sp,
                        color = TextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  painter=painterResource(R.drawable.calender_icon),
                contentDescription = null,

                    tint = IconActive,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sunday, Dec 14, 2025",
                    fontSize = 14.sp,
                    color = Color.Black


                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.baseline_access_time_24),
                    contentDescription = null,
                    tint = IconActive,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "14:30",
                    fontSize = 14.sp,
                    color = Color.Black

                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.location),
                    contentDescription = null,
                    tint = IconActive,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Heart Care Clinic",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}


