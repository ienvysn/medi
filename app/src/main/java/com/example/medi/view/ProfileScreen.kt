package com.example.medi.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medi.model.userModel
import com.example.medi.repository.userRepoImpl
import com.example.medi.ui.theme.*
import com.example.medi.viewModel.UserViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.clickable
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val userViewModel = remember { UserViewModel(userRepoImpl()) }
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userData by userViewModel.user.observeAsState()

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null && currentUser != null) {
                userViewModel.uploadProfileImage(context, uri) { success, result ->
                    if (success) {
                        val currentData = userData ?: return@uploadProfileImage
                        val updatedUser = currentData.copy(profileImageUrl = result)
                        userViewModel.updateUser(currentData.userId, updatedUser) { s, m ->
                            Toast.makeText(context, if(s) "Image Updated" else m, Toast.LENGTH_SHORT).show()
                            if (s) {
                                userViewModel.getUserById(currentData.userId) // Refresh
                            }
                        }
                    } else {
                        Toast.makeText(context, "Upload Failed: $result", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )

    // Fetch user data on mount
    LaunchedEffect(currentUser?.uid) {
        currentUser?.uid?.let { uid ->
            userViewModel.getUserById(uid)
        }
    }

    // Editable States
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var bloodType by remember { mutableStateOf("") }

    // Update local state when userData arrives
    LaunchedEffect(userData) {
        userData?.let {
            name = it.name
            email = it.email
            dateOfBirth = it.dateOfBirth
            bloodType = it.bloodType
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = DarkTextColor
                )
                Text(
                    text = "Manage your personal information",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Button(
                onClick = {
                    if (isEditing) {
                        // Save functionality
                        currentUser?.uid?.let { uid ->
                            val updatedUser = userModel(
                                userId = uid,
                                name = name,
                                email = email,
                                password = userData?.password ?: "",
                                dateOfBirth = dateOfBirth,
                                bloodType = bloodType,
                                profileImageUrl = userData?.profileImageUrl ?: ""
                            )

                            userViewModel.updateUser(uid, updatedUser) { success, message ->
                                if (success) {
                                    Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show()
                                    isEditing = false
                                } else {
                                    Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        isEditing = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = IconActive
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = if (isEditing) "Save" else "Edit")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // --- Profile Avatar ---
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(IconActive)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (!userData?.profileImageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = userData!!.profileImageUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = getInitials(name),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name.ifEmpty { "User Name" },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black


            )
            Text(
                text = email.ifEmpty { "email@example.com" },
                style = MaterialTheme.typography.bodyMedium,
                color = TextColor
            )
        }

        Spacer(modifier = Modifier.height(30.dp))


        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderOutline)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                ProfileInputField(
                    label = "Full Name",
                    value = name,
                    onValueChange = { name = it },
                    icon = Icons.Default.Person,
                    isEditable = isEditing
                )

                ProfileInputField(
                    label = "Email",
                    value = email,
                    onValueChange = { /* Email usually not editable here directly */ },
                    icon = Icons.Default.Email,
                    isEditable = false // Disable email editing for simplicity now
                )

                ProfileInputField(
                    label = "Date of Birth",
                    value = dateOfBirth,
                    onValueChange = { dateOfBirth = it },
                    icon = Icons.Default.DateRange,
                    isEditable = isEditing
                )

                ProfileInputField(
                    label = "Blood Type",
                    value = bloodType,
                    onValueChange = { bloodType = it },
                    icon = Icons.Default.Face,
                    isEditable = isEditing
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))



        OutlinedButton(
            onClick = {
                userViewModel.logOut { success, message ->
                    if (success) {
                        val intent = Intent(context, LoginScreen::class.java)

                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Destructive),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Destructive)
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign Out", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun ProfileInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    isEditable: Boolean
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = IconInacive, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, style = MaterialTheme.typography.bodyMedium, color = IconInacive)
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isEditable) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = InputBackground,
                    unfocusedContainerColor = InputBackground,
                    focusedIndicatorColor = IconActive,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledContainerColor = InputBackground.copy(alpha = 0.5f)
                )
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(InputBackground, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = value.ifEmpty { "-" },
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
        }
    }
}

fun getInitials(name: String): String {
    if (name.isBlank()) return "AB"
    val parts = name.trim().split(" ")
    return if (parts.size == 1) {
        parts[0].take(2).uppercase()
    } else {
        (parts[0].first().toString() + parts.last().first().toString()).uppercase()
    }
}


