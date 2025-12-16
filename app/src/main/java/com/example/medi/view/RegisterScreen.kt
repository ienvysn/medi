package com.example.medi.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medi.R
import com.example.medi.model.userModel
import com.example.medi.repository.userRepoImpl
import com.example.medi.ui.theme.InputBackground
import com.example.medi.viewModel.UserViewModel
import java.util.Calendar


class RegisterScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegisterBody()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterBody() {
    var password by remember {
        mutableStateOf("")
    }
    var visibility by remember {
        mutableStateOf(false)
    }
    var userViewModel = remember {   UserViewModel(userRepoImpl())}

    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    var calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)
    var month = calendar.get(Calendar.MONTH)
    var day = calendar.get(Calendar.DAY_OF_MONTH)
    var selectedDate by remember { mutableStateOf("") }

    val context = LocalContext.current
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, y: Int, m: Int, d: Int ->
                selectedDate = "$y/${m + 1}/$d"
            },
            year,
            month,
            day
        )
    }


    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding).background(Color(0xFFFFFFFF))

        ) {


            Image(
                painter = painterResource(R.drawable.loginimage),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .height(300.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )


            Surface(
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                border = BorderStroke(2.dp, Color(0xFFD3D6DB)),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

                LazyColumn(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item {
                        Text("Register", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp), modifier = Modifier.padding(vertical = 10.dp))
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color.Gray, fontSize = 16.sp)) {
                                    append("Already have an Account? ")
                                }
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)) {
                                    append("Login")
                                }
                            }
                        ,
                            modifier = Modifier.clickable {
                                context.startActivity(Intent(context, LoginScreen::class.java))
                            }
                        )


                        Spacer(modifier = Modifier.height(20.dp));

                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            leadingIcon = {
                                Icon(
                                    painterResource(R.drawable.baseline_person_24),
                                    contentDescription = "Email Icon"
                                )
                            },
                            label = {
                                Text(
                                    "Enter Your Username",
                                    style = TextStyle(color = Color.Black)
                                )
                            },

                            shape = RoundedCornerShape(15.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,

                                ),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = InputBackground,
                                unfocusedContainerColor = InputBackground,
                                focusedIndicatorColor = Color.Gray,
                                unfocusedIndicatorColor = Color.Transparent
                            ),

                            )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            leadingIcon = {
                                Icon(
                                    painterResource(R.drawable.baseline_email_24),
                                    contentDescription = "Email Icon"
                                )
                            },
                            label = { Text("Enter Your Email", style = TextStyle(color = Color.Black)) },

                            shape = RoundedCornerShape(15.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,

                                ),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = InputBackground,
                                unfocusedContainerColor = InputBackground,
                                focusedIndicatorColor = Color.Gray,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = selectedDate,
                            onValueChange = {},
                            leadingIcon = {
                                Icon(
                                    painterResource(R.drawable.calender_icon),
                                    contentDescription = "DOB",
                                    tint = Color.Black,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    "Date of Birth",
                                    style = TextStyle(color = Color.Black)
                                )
                            },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                                .clickable { datePickerDialog.show() },
                            readOnly = true,
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = InputBackground,
                                unfocusedContainerColor = InputBackground,
                                focusedIndicatorColor = Color.Gray,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(

                            value = password,
                            onValueChange = { password = it },
                            leadingIcon = {
                                Icon(
                                    painterResource(R.drawable.baseline_lock_open_24),
                                    contentDescription = "Email Icon"
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = {
                                    visibility = !visibility
                                }) {
                                    Icon(
                                        painter = if (visibility) {
                                            painterResource(R.drawable.baseline_visibility_24)
                                        } else {
                                            painterResource(R.drawable.baseline_visibility_off_24)
                                        },
                                        contentDescription = null
                                    )
                                }

                            },
                            visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
                            shape = RoundedCornerShape(15.dp),
                            label = {
                                Text("Enter your password",style = TextStyle(color = Color.Black))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = InputBackground,
                                unfocusedContainerColor = InputBackground,
                                focusedIndicatorColor = Color.Gray,
                                unfocusedIndicatorColor = Color.Transparent
                            )

                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        Button(
                            onClick = {  if (username.isEmpty()) {
                                Toast.makeText(context, "Please enter a username", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                                if (email.isEmpty() || !email.contains("@")) {
                                    Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                             
                                if (password.isEmpty()) {
                                    Toast.makeText(context, "Please enter a password", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                userViewModel.register(email,password) { success, message, userId ->
                                    if (success) {

                                        val model= userModel(
                                            userId,
                                            username,
                                            email,
                                            password,
                                            selectedDate,
                                            ""
                                        )
                                        userViewModel.addUserToDatabase(userId,model) { success, message ->
                                            if (success) {
                                                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
                                                val intent = Intent(context,
                                                    Dashboard::class.java)
                                                context.startActivity(intent)
                                            }
                                            else{
                                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                    }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF009688),
                            )
                        ) {
                            Text(text = "Register", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Divider(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp),
                                color = Color(0xFFD3D6DB),
                                thickness = 1.dp
                            )
                            Text(
                                text = "OR Continue With",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                            Divider(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp),
                                color = Color(0xFFD3D6DB),
                                thickness = 1.dp
                            )
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        OutlinedButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, Color(0xFFD3D6DB))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.googlelogo),
                                contentDescription = "Google login",
                                tint = Color.Unspecified

                            )
                        }
                    }
                }

            }
        }
    }
}


@Preview
@Composable
fun RegisterPreview(){
    RegisterBody()
}