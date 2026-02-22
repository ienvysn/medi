package com.example.medi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.medi.model.userModel
import com.example.medi.repository.userRepo
import com.example.medi.viewModel.UserViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UserViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repo: userRepo = mock()
    private val viewModel = UserViewModel(repo)

    @Test
    fun login_success_test() {
        val email = "test@gmail.com"
        val password = "123"

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "Login Successful")
            null
        }.`when`(repo).login(eq(email), eq(password), any())

        var successResult = false
        var messageResult = ""

        viewModel.login(email, password) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Login Successful", messageResult)
        verify(repo).login(eq(email), eq(password), any())
    }

    @Test
    fun login_failure_test() {
        val email = "wrong@gmail.com"
        val password = "wrong"

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(false, "Invalid credentials")
            null
        }.`when`(repo).login(eq(email), eq(password), any())

        var successResult = true
        var messageResult = ""

        viewModel.login(email, password) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(!successResult)
        assertEquals("Invalid credentials", messageResult)
        verify(repo).login(eq(email), eq(password), any())
    }
}
