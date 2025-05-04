package com.example.appadvisor.data.model.request

import com.example.appadvisor.data.model.Department
import com.example.appadvisor.data.model.Role

data class SignUpRequest(
    var email: String = "",
    var name: String = "",
    var password: String = "",
    var role: Role = Role.STUDENT,
    var department: Department? = null
)
