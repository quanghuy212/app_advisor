package com.example.appadvisor.ui.screen.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.DailyTasks
import com.example.appadvisor.data.Task
import com.example.appadvisor.data.model.enums.Status
import com.example.appadvisor.data.model.request.TaskRequest
import com.example.appadvisor.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    // Add task UI State
    private var _addTaskUiState = MutableStateFlow(TaskUiState())
    val addTaskUiState: StateFlow<TaskUiState> = _addTaskUiState

    // Edit task UI State
    private var _editTaskUiState = MutableStateFlow<TaskUiState?>(null)
    val editTaskUiState: StateFlow<TaskUiState?> = _editTaskUiState
    // Selected date
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    // All tasks grouped by date
    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> = _allTasks

    private val _dailyTasksList = MutableStateFlow<List<DailyTasks>>(emptyList())
    val dailyTasksList: StateFlow<List<DailyTasks>> = _dailyTasksList

    // UI state for add bottom sheet
    private val _showAddTaskBottomSheet = MutableStateFlow(false)
    val showAddTaskBottomSheet: StateFlow<Boolean> = _showAddTaskBottomSheet

    // Ui State for edit bottom sheet
    private val _showEditTaskBottomSheet = MutableStateFlow(false)
    val showEditTaskBottomSheet: StateFlow<Boolean> = _showEditTaskBottomSheet

    // UI state show delete confirm dialog
    private val _showDeleteConfirmDialog = MutableStateFlow(false)
    val showDeleteConfirmDialog: StateFlow<Boolean> = _showDeleteConfirmDialog

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
    }

    fun onTitleChange(title: String) {
        _addTaskUiState.update {
            it.copy(title = title)
        }
    }

    fun onDescriptionChange(description: String) {
        _addTaskUiState.update {
            it.copy(description = description)
        }
    }

    fun onStartDateChange(startDate: LocalDate) {
        _addTaskUiState.update {
            it.copy(startDate = startDate)
        }
    }
    fun onStartTimeChange(startTime: LocalTime) {
        _addTaskUiState.update {
            it.copy(startTime = startTime)
        }
    }

    fun onEndTimeChange(endTime: LocalTime) {
        _addTaskUiState.update {
            it.copy(endTime = endTime)
        }
    }

    fun onEditTitleChange(newTitle: String) {
        _editTaskUiState.update {
            it?.copy(title = newTitle)
        }
    }

    fun onEditDescriptionChange(newDesc: String) {
        _editTaskUiState.update {
            it?.copy(description = newDesc)
        }
    }

    fun onEditStartDateChange(newStartDate: LocalDate) {
        _editTaskUiState.update {
            it?.copy(startDate = newStartDate)
        }
    }

    fun onEditStartTimeChange(newStartTime: LocalTime) {
        _editTaskUiState.update {
            it?.copy(startTime = newStartTime)
        }
    }

    fun onEditEndTimeChange(newEndTime: LocalTime) {
        _editTaskUiState.update {
            it?.copy(endTime = newEndTime)
        }
    }

    fun setEditTask(task: Task) {
        _editTaskUiState.value = TaskUiState(
            title = task.title,
            description = task.description,
            startDate = LocalDate.parse(task.startDate),
            startTime = LocalTime.parse(task.startTime),
            endTime = LocalTime.parse(task.endTime)
        )
    }

    fun loadTasks() {
        viewModelScope.launch {
            try {
                val tasks = taskRepository.getTasksListByUser()
                _allTasks.value = tasks

                _dailyTasksList.value = groupTasksByDate(tasks)
            } catch (e: Exception) {
                // Xử lý lỗi nếu cần
                Log.e("CalendarViewModel", "Error loading tasks", e)
            }
        }
    }

    private fun groupTasksByDate(tasks: List<Task>): List<DailyTasks> {
        return tasks
            .filter { it.startDate != null }
            .groupBy { LocalDate.parse(it.startDate) }
            .map { (date, tasks) ->
                DailyTasks(
                    date = date,
                    tasks = tasks.sortedBy { it.status == Status.DONE }
                )
            }
            .sortedBy { it.date }
    }


    fun toggleTaskCompletion(task: Task, date: LocalDate) {
        viewModelScope.launch {
            try {
                val newStatus = if (task.status == Status.DONE) Status.PLANNED else Status.DONE
                Log.d("CalendarViewModel", "Toggle task: ${task.id} - ${task.status}")
                val updatedTask = task.copy(status = newStatus)
                Log.d("CalendarViewmodel","Updated task: $updatedTask")
                val result = taskRepository.updateTask(updatedTask)
                result.fold(
                    onSuccess = {
                        Log.d("CalendarViewModel", "Task updated successfully.")
                        loadTasks()
                    },
                    onFailure = {
                        Log.e("CalendarViewModel", "Failed to update task: ${it.message}")
                    }
                )
            } catch (e: Exception) {
                Log.e("CalendarViewModel", "Exception during update", e)
            }
        }
    }

    fun updateTask(taskId: Long) {
        viewModelScope.launch {
            try {
                val orginalTask = allTasks.value.find { it.id == taskId }
                if (orginalTask == null) {
                    Log.e("Calendar Viewmodel", "Not found task with TaskId = $taskId")
                }

                val updatedTask = orginalTask?.copy(
                    title = _editTaskUiState.value!!.title,
                    description = _editTaskUiState.value!!.description,
                    startDate = _editTaskUiState.value!!.startDate.toString(),
                    startTime = _editTaskUiState.value!!.startTime.toString(),
                    endTime = _editTaskUiState.value!!.endTime.toString()
                )

                val result = taskRepository.updateTask(updatedTask!!)

                result.fold(
                    onSuccess = {
                        Log.d("CalendarViewModel", "Task updated successfully.")
                        loadTasks()
                        closeEditTaskBottomSheet()
                    },
                    onFailure = {
                        Log.e("CalendarViewModel", "Failed to update task: ${it.message}")
                    }
                )
            } catch (e: Exception) {
                Log.e("CalendarViewModel", "Error edit task", e)
            }
        }
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            val result = taskRepository.deleteTask(taskId)
            result.fold(
                onSuccess = {
                    loadTasks()
                    Log.d("CalendarViewModel", "Task deleted successfully")
                },
                onFailure = {
                    Log.e("CalendarViewModel", "Failed to delete task: ${it.message}")
                }
            )
        }
    }

    fun saveTask() {
        viewModelScope.launch {
            try {
                val request = TaskRequest(
                    title = _addTaskUiState.value.title,
                    description = _addTaskUiState.value.description,
                    startDate = _selectedDate.value.toString(),
                    startTime = _addTaskUiState.value.startTime.toString(),
                    endTime = _addTaskUiState.value.endTime.toString()
                )

                Log.d("Calendar viewmodel", "Start save task")
                val result = taskRepository.saveTask(request)

                result.fold(
                    onSuccess = {
                        Log.d("Calendar viewmodel","Save task successfully: $request")
                        loadTasks()
                    },
                    onFailure = {
                        Log.e("Calendar ViewModel",it.message.toString())
                    }
                )

            } catch (e: Exception) {
                Log.e("CalendarViewModel", "Error adding task", e)
            }
            loadTasks()
        }
    }

    fun toggleAddTaskBottomSheet() {
        _showAddTaskBottomSheet.update { !it }
    }

    fun closeAddTaskBottomSheet() {
        _showAddTaskBottomSheet.value = false
    }

    fun toggleEditTaskBottomSheet() {
        _showEditTaskBottomSheet.update { !it }
    }

    fun closeEditTaskBottomSheet() {
        _showEditTaskBottomSheet.value = false
    }

    fun showConfirmDeleteDialog() {
        _showDeleteConfirmDialog.update { !it }
    }

    fun closeConfirmDeleteTaskDialog() {
        _showDeleteConfirmDialog.value = false
    }

}