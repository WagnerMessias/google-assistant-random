package com.example.demo

import java.io.File


class PersonFactory {

    companion object {
        fun getParticipants(amount: Int): MutableList<Person> {

            //TODO conditional from participants' source between CSV and conversation data
            var  participants =  getParticpantsFromCSV()

            val lucly: MutableList<Person> = mutableListOf()

            for (x in 1..amount) {
                if(participants.size > 0) {
                    val chosenPerson = participants.random()
                    lucly.add(chosenPerson)
                    participants.remove(chosenPerson)
                }
            }

            return lucly

            //TODO save participants list to conversation data
//            val gson = GsonBuilder().setPrettyPrinting().create()
//            val jsonPersonList: String = gson.toJson(participants)
//            println(jsonPersonList)

        }

        fun getParticpantsFromCSV(): MutableList<Person> {

            val participants: MutableList<Person> = mutableListOf()

            val path = MyAction::class.java.getResource("/participants.csv").toURI()

            File(path).readLines().forEach {line -> val tokens = line.split(",")
                participants.add(Person(tokens[0].trim(),tokens[1].trim()))
            }

            return  participants
        }
    }


}