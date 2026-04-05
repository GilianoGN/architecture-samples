package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.android.architecture.blueprints.todoapp.TodoActivity
import com.example.android.architecture.blueprints.todoapp.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TasksScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<TodoActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun displayPageTitle_whenScreenIsLoaded() {
        // Verifica se o título "Tarefas" aparece na tela
        val context = composeTestRule.activity
        val expectedTitle = context.getString(R.string.label_all)
        
        composeTestRule.onNodeWithText(expectedTitle).assertIsDisplayed()
    }

    @Test
    fun noTasksLabel_isDisplayed_whenListIsEmpty() {
        // Verifica se a mensagem de "Você não tem tarefas" aparece quando não há dados
        val context = composeTestRule.activity
        val noTasksText = context.getString(R.string.no_tasks_all)
        
        composeTestRule.onNodeWithText(noTasksText).assertIsDisplayed()
    }
}