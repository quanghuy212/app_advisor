package com.example.appadvisor.ui.screen.appointment

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.model.Meeting
import com.example.appadvisor.data.model.ParticipantInfo
import com.example.appadvisor.data.model.enums.MeetingStatus
import com.example.appadvisor.data.model.enums.ParticipantStatus
import com.example.appadvisor.data.model.enums.Role
import com.example.appadvisor.data.model.request.MeetingRequest
import com.example.appadvisor.data.model.request.UpdateMeetingRequest
import com.example.appadvisor.data.model.response.MeetingResponse
import com.example.appadvisor.data.model.response.StudentResponseDTO
import com.example.appadvisor.data.repository.AdvisorRepository
import com.example.appadvisor.data.repository.MeetingRepository
import com.example.appadvisor.data.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Thread.State
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MeetingViewModel @Inject constructor(
    private val meetingRepository: MeetingRepository,
    private val advisorRepository: AdvisorRepository,
    private val studentRepository: StudentRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    // Giả lập dữ liệu ban đầu, bạn sẽ load từ API sau
    private val _uiState = MutableStateFlow(MeetingScreenUiState())
    val uiState: StateFlow<MeetingScreenUiState> = _uiState

    // Add Meeting Ui State
    private val _addUiState = MutableStateFlow(MeetingUiState())
    val addUiState: StateFlow<MeetingUiState> = _addUiState
    
    private val _editUiState = MutableStateFlow(MeetingUiState())
    val editUiState: StateFlow<MeetingUiState> = _editUiState
    
    private val _isShowSelectStudentsList = MutableStateFlow(false)
    val isShowSelectStudentsList: StateFlow<Boolean> = _isShowSelectStudentsList

    // id by current user -- only need by student
    private val _studentId = MutableStateFlow("")
    val studentId: StateFlow<String> = _studentId

    private val _isShowEditDialog = MutableStateFlow(false)
    val showEditDialog : StateFlow<Boolean> = _isShowEditDialog

    var currentEditingMeetingId: Long? = null

    var role by mutableStateOf<Role?>(null)
        private set

    init {
        getRole()
    }

    private fun getRole() {
        viewModelScope.launch {
            role = tokenManager.getRole()?.let { Role.valueOf(it) }
        }
    }

    fun openEditDialog() {
        _isShowEditDialog.value = true
    }

    fun closeEditDialog() {
        _isShowEditDialog.value = false
    }

    fun onTitleChange(title: String) {
        _addUiState.update { currentState ->
            currentState.copy(title = title)
        }
    }

    fun onDescriptionChange(description: String) {
        _addUiState.update { currentState ->
            currentState.copy(description = description)
        }
    }

    fun onStatusChange(status: MeetingStatus) {
        _addUiState.update { currentState ->
            currentState.copy(status = status)
        }
    }

    fun onMeetingDateChange(date: LocalDate) {
        _addUiState.update { currentState ->
            currentState.copy(meetingDate = date)
        }
    }

    fun onStartTimeChange(time: LocalTime) {
        _addUiState.update { currentState ->
            currentState.copy(startTime = time)
        }
    }

    fun onEndTimeChange(time: LocalTime) {
        _addUiState.update { currentState ->
            currentState.copy(endTime = time)
        }
    }

    fun onParticipantsChange(participants: List<StudentResponseDTO>) {
        _addUiState.update { currentState ->
            currentState.copy(selectedParticipants = participants)
        }
    }

    fun onEditTitleChange(newTitle: String) {
        _editUiState.update { it.copy(title = newTitle) }
    }

    fun onEditDescriptionChange(newDescription: String) {
        _editUiState.update { it.copy(description = newDescription) }
    }

    fun onEditMeetingDateChange(newDate: LocalDate) {
        _editUiState.update { it.copy(meetingDate = newDate) }
    }

    fun onEditStartTimeChange(newTime: LocalTime) {
        _editUiState.update { it.copy(startTime = newTime) }
    }

    fun onEditEndTimeChange(newTime: LocalTime) {
        _editUiState.update { it.copy(endTime = newTime) }
    }

    fun onEditParticipantsChange(newParticipants: List<StudentResponseDTO>) {
        _editUiState.update { it.copy(selectedParticipants = newParticipants) }
    }


    fun selectTab(tab: String) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun updateMeeting() {
        viewModelScope.launch {
            val state = editUiState.value
            val updateMeetingRequest = UpdateMeetingRequest(
                title = state.title,
                description = state.description,
                meetingDate = state.meetingDate.toString(),
                startTime = state.startTime.toString(),
                endTime = state.endTime.toString(),
                participants = state.selectedParticipants.map {
                    ParticipantInfo(
                        studentId = it.id,
                        studentName = it.name,
                        status = ParticipantStatus.PENDING
                    )
                }
            )

            try {
                val id = currentEditingMeetingId
                val response = meetingRepository.updateMeeting(id!!, updateMeetingRequest)
                response.fold(
                    onSuccess = {
                        Log.d("Meeting viewmodel","update meeting done")
                        loadMeetings()
                        Log.d("Meeting viewmodel","load done")
                    },
                    onFailure = {
                        Log.e("Meeting viewmodel","update meeting fail")
                    }
                )
            } catch (e: Exception) {
                Log.e("Meeting viewModel","Fail: $e")
            }
        }
    }

    fun setEditMeeting(meeting: Meeting) {
        currentEditingMeetingId = meeting.id
        val mappedParticipants = meeting.participants.map { participant ->
            StudentResponseDTO(
                id = participant.studentId,
                name = participant.studentName
            )
        }
        _editUiState.value = MeetingUiState(
            title = meeting.title,
            description = meeting.description,
            meetingDate = LocalDate.parse(meeting.meetingDate),
            startTime = LocalTime.parse(meeting.startTime),
            endTime = LocalTime.parse(meeting.endTime),
            selectedParticipants = mappedParticipants
        )
    }
    
    fun deleteMeeting(id: Long) {
        viewModelScope.launch {
            try {
                val response = meetingRepository.deleteMeeting(id)
                response.fold(
                    onSuccess = {
                        Log.d("Meeting viewmodel","delete done")
                        loadMeetings()
                        Log.d("Meeting viewmodel","load done")
                    },
                    onFailure = {
                        Log.e("Meeting viewmodel","delete fail")
                    }
                )
            } catch (e: Exception) {
                Log.e("Meeting viewModel","Fail: $e")
            }
        }
    }

    fun loadMeetings() {
        viewModelScope.launch {
            try {
                val meetingsList = meetingRepository.getAllMeetingsByUser()
                Log.d("MeetingViewModel", "Fetched ${meetingsList.size} meetings")
                meetingsList.forEach {
                    Log.d("MeetingViewModel", "Meeting: ${it.title} - ${it.meetingStatus} at ${it.meetingDate}")
                }
                _uiState.update {
                    it.copy(meetings = meetingsList)
                }
            } catch (e: Exception) {
                Log.e("MeetingViewModel", "Error get all meetings", e)
            }
        }
    }

    private fun checkMeetings(id: Long) {
        viewModelScope.launch {
            val response = meetingRepository.updateMeetingStatus(id)
            response.fold(
                onSuccess = {
                    Log.d("Meeting viewmodel","Update done")
                    loadMeetings()
                    Log.d("Meeting viewmodel","load done")
                },
                onFailure = {
                    Log.e("Meeting viewmodel","Update fail")
                }
            )
        }
    }

    fun acceptResponse(meetingId: Long) {
        viewModelScope.launch {
            val response = meetingRepository.respondToMeeting(
                id = meetingId,
                participantStatus = ParticipantStatus.ACCEPTED
            )
            response.fold(
                onSuccess = {
                    Log.d("Meeting viewmodel","Accept done")
                    checkMeetings(meetingId)
                },
                onFailure = {
                    Log.e("Meeting viewmodel","Accept fail")
                }
            )
        }
    }

    fun declineResponse(meetingId: Long) {
        viewModelScope.launch {
            val response = meetingRepository.respondToMeeting(
                id = meetingId,
                participantStatus = ParticipantStatus.DECLINED
            )
            response.fold(
                onSuccess = {
                    Log.d("Meeting viewmodel","Decline done")
                    loadMeetings()
                    checkMeetings(meetingId)
                },
                onFailure = {
                    Log.e("Meeting viewmodel","Decline fail")
                }
            )
        }
    }

    fun getStudentId() {
        viewModelScope.launch {
            val studentId = studentRepository.getStudentId()
            Log.d("MeetingViewModel", "ID: $studentId")
            _studentId.value = studentId
        }
    }

    fun loadListStudentsByAdvisor() {
        viewModelScope.launch {
            val studentsList = advisorRepository.getAllStudentsByAdvisor()
            Log.d("MeetingViewModel","Fetch: ${studentsList.size}")

            _addUiState.update {
                it.copy(students = studentsList)
            }

            _editUiState.update { it.copy(
                students = studentsList
            ) }
        }
    }

    fun saveMeetings() {
        val uiState = _addUiState.value

        val idListParticipants = uiState.selectedParticipants.map { it.id }
        Log.d("Meeting ViewModel","ID List of selected students: $idListParticipants")

        val meetingRequest = MeetingRequest(
            title = uiState.title,
            description = uiState.description,
            meetingDate = uiState.meetingDate.format(DateTimeFormatter.ISO_DATE),
            startTime = uiState.startTime.format(DateTimeFormatter.ISO_TIME),
            endTime = uiState.endTime.format(DateTimeFormatter.ISO_TIME),
            participants = idListParticipants
        )

        viewModelScope.launch {
            val result = meetingRepository.saveMeeting(meetingRequest)
            result.fold(
                onSuccess = {
                    Log.d("Meeting ViewModel", "Meet saved successfully.")
                    loadMeetings()
                },
                onFailure = {
                    Log.e("Meeting ViewModel", "Failed to save task: ${it.message}")
                }
            )
        }
    }

    fun getFilteredAppointments(): List<Meeting> {
        return _uiState.value.meetings.filter {
            it.meetingStatus == MeetingStatus.valueOf(_uiState.value.selectedTab)
        }
    }

    fun openSelectStudentsDialog() {
        _isShowSelectStudentsList.value = true
    }

    fun closeSelectStudentsDialog() {
        _isShowSelectStudentsList.value = false
    }
}
