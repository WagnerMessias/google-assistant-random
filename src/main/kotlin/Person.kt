package com.example.demo

data class Person(val name: String, val id: String){
    fun getLinkPerfilMeetup() = "https://www.meetup.com/kotlin-meetup-sp/members/${id.trim()}"
}
