package com.example.medi.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medi.R
import com.example.medi.repository.userRepoImpl
import com.example.medi.ui.theme.InputBackground
import com.example.medi.ui.theme.black
import com.example.medi.viewModel.UserViewModel


class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginBody()
            }
        }
    }

@Composable
fun LoginBody() {
    var password by remember {
        mutableStateOf("")
    }
    var visibility by remember {
        mutableStateOf(false)
    }
    var checked by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var userViewModel = remember {   UserViewModel(userRepoImpl())}
    val context = LocalContext.current

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFFFFFF))

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
                        Text("Login", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp), modifier = Modifier.padding(vertical = 10.dp))
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color.Gray, fontSize = 16.sp)) {
                                    append("Don't have an Account? ")
                                }
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)) {
                                    append("Sign Up")
                                }
                            }
                            ,
                            modifier = Modifier.clickable {
                                context.startActivity(Intent(context, RegisterScreen::class.java))
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp));


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

                        Spacer(modifier = Modifier.padding(vertical = 10.dp))


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
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {


                            TextButton(onClick = {
                                val intent = Intent(context, ForgetPasswordScreen::class.java)
                            },modifier = Modifier.weight(2f)) {
                                Text("Forgot Password?", style = TextStyle(fontSize = 16.sp, color = black))
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(25.dp))

                        Button(
                            onClick = {

                                if (email.isEmpty() || !email.contains("@")) {
                                    Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                if (password.isEmpty()) {
                                    Toast.makeText(context, "Please enter a password", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                userViewModel.login(email,password) { success, message ->
                                    if (success) {
                                        Toast.makeText(context, "Logged In", Toast.LENGTH_SHORT).show()
                                        context.startActivity(
                                            Intent(
                                                context,
                                                Dashboard::class.java
                                            )
                                        )
                                    }else{
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
                            Text(text = "Login", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }

                    item {
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
                    }

                    item {
                        Spacer(modifier = Modifier.height(25.dp))

                        OutlinedButton(
                            onClick = {

                            },
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
fun LoginPreview(){
    LoginBody()
}

