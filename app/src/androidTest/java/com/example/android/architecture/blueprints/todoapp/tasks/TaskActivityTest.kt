package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.android.architecture.blueprints.todoapp.TodoActivity
import com.example.android.architecture.blueprints.todoapp.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.*

@HiltAndroidTest
class TasksActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    // Esta regra inicia a TodoActivity automaticamente
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<TodoActivity>()

    @Test
    fun clickAddTaskButton_opensAddEditScreen() {
        hiltRule.inject()

        // Buscamos pela descrição de conteúdo
        val addTaskDescription = composeTestRule.activity.getString(R.string.add_task)
        
        composeTestRule
            .onNodeWithContentDescription(addTaskDescription)
            .performClick()
    }

    @Test
    fun createNewTask_isDisplayedInList() {
        hiltRule.inject()

        // 1. Clica no botão de adicionar
        val addTaskDescription = composeTestRule.activity.getString(R.string.add_task)
        composeTestRule
            .onNodeWithContentDescription(addTaskDescription)
            .performClick()

        // 2. Digita o título
        val titleText = "Estudar Automação"
        val descriptionText = "Praticar Espresso e Compose"
        
        // Buscamos pelos campos de texto
        composeTestRule
            .onAllNodes(hasSetTextAction())[0]
            .performTextInput(titleText)
        
        composeTestRule
            .onAllNodes(hasSetTextAction())[1]
            .performTextInput(descriptionText)

        // 3. Clica no botão de salvar
        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.cd_save_task))
            .performClick()

        // 4. VERIFICAÇÃO: A tarefa aparece na lista?
        composeTestRule
            .onNodeWithText(titleText)
            .assertIsDisplayed()
    }

    @Test
    fun editTask_isDisplayedInList() {
        hiltRule.inject()

        // 1. Salvar uma tarefa inicial
        // Vamos criar uma tarefa primeiro para poder editá-la
        createNewTask("Tarefa Original", "Descricao Original")

        // 2. Clicar na tarefa para ver detalhes
        composeTestRule
            .onNodeWithText("Tarefa Original")
            .performClick()

        // 3. Clicar no FAB de editar
        val editDescription = composeTestRule.activity.getString(R.string.edit_task)
        composeTestRule
            .onNodeWithContentDescription(editDescription)
            .performClick()

        // 4. Editar os campos
        val novoTitulo = "Tarefa Editada"
        val novaDescricao = "Nova Descricao"
        
        composeTestRule
            .onAllNodes(hasSetTextAction())[0]
            .performTextReplacement(novoTitulo)
        
        composeTestRule
            .onAllNodes(hasSetTextAction())[1]
            .performTextReplacement(novaDescricao)

        // 5. Salvar
        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.cd_save_task))
            .performClick()

        // 6. Verificar se a antiga sumiu e a nova apareceu
        composeTestRule
            .onNodeWithText("Tarefa Original")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText(novoTitulo)
            .assertIsDisplayed()
    }

    // Função auxiliar para evitar repetição de código
    private fun createNewTask(title: String, description: String) {
        val addTaskDescription = composeTestRule.activity.getString(R.string.add_task)
        composeTestRule
            .onNodeWithContentDescription(addTaskDescription)
            .performClick()
        composeTestRule
            .onAllNodes(hasSetTextAction())[0]
            .performTextInput(title)
        composeTestRule
            .onAllNodes(hasSetTextAction())[1]
            .performTextInput(description)
        
        val saveTaskDescription = composeTestRule.activity.getString(R.string.cd_save_task)
        composeTestRule
            .onNodeWithContentDescription(saveTaskDescription)
            .performClick()
    }
}