
package com.example.demo

import com.google.actions.api.App
import com.google.gson.GsonBuilder
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.util.toMap


fun Application.main() {
    // This uses use the logger to log every call (request/response)
    install(CallLogging)

    val actionApp: App = MyAction()

    install(ContentNegotiation) {
        gson {}
    }

    routing {
        post("/fulfillment") {
            val requestBodyObject = call.receiveOrNull<Any>()
            val gson = GsonBuilder().create() // for pretty print feature
            val bodyJson = gson.toJson(requestBodyObject)
            val headersMap = call.request.headers.toMap()

            call.respond(actionApp.handleRequest(bodyJson, headersMap).get())
        }

        get("/") {
            call.respondText("Random Action", ContentType.Text.Plain)
        }

    }
}