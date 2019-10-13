package com.example.demo


import com.google.actions.api.*
import com.google.actions.api.Capability
import com.google.api.services.actions_fulfillment.v2.model.*


class MyAction: DialogflowApp() {


    @ForIntent("welcome")
    fun welcome(request: ActionRequest): ActionResponse {

        val responseBuilder = getResponseBuilder(request)

        responseBuilder.add("<speak>" +
                                "<emphasis level=\"strong\">Bem-vindo ao Sorteador do Kotlin Everywhere São Paulo,</emphasis> <break  time=\"300ms\"/>" +
                                "Quantos participantes você deseja sortear?" +
                            "</speak>")


        if (request.hasCapability(Capability.SCREEN_OUTPUT.value)) {

            responseBuilder.add(
                    BasicCard()
                            .setTitle("Kotlin Everywhere 2019")
                            .setSubtitle("São Paulo")
                            .setImage(
                                    Image()
                                        .setUrl("https://secure.meetupstatic.com/photos/event/2/c/5/a/600_481811354.jpeg")
                                        .setAccessibilityText("Imagem do Kotlin Everywhere"))
                            .setImageDisplayOptions("CROPPED")
            )
        }

        return responseBuilder.build()
    }


    @ForIntent("sortear")
    fun realizarSorteio(request: ActionRequest): ActionResponse{

        val amount = (request.getParameter("quantidade") as Double).toInt()

        val responseBuilder = getResponseBuilder(request)


        var luckyPeople = PersonFactory.getParticipants(amount)

        if(luckyPeople.size <= 0){
            responseBuilder.add("As pessoas já foram sorteadas" )
            responseBuilder.endConversation()

        }else if(luckyPeople.size > 1){

            var listCarousel = ArrayList<CarouselBrowseItem>()

            var nomes = ""
            luckyPeople.forEach{
                nomes += it.name+ ", "

                listCarousel.add(
                        CarouselBrowseItem()
                                .setTitle(it.name)
                                .setDescription(it.id)
                                .setOpenUrlAction( OpenUrlAction().setUrl(it.getLinkPerfilMeetup()))
                                .setImage(
                                        Image().setUrl(
                                                "http://bit.ly/2nqKaUk")
                                                .setAccessibilityText("Image")))
            }

            responseBuilder.add("As pessoas sorteadas foram: $nomes" )

            responseBuilder.add(
                    CarouselBrowse()
                            .setItems(listCarousel)
            )

        }else{
            luckyPeople.get(0).let {person ->
                responseBuilder.add("A pessoa sorteada foi: ${person.name} seu ID é  ${person.id}" )
            }
        }

        return responseBuilder.build()
    }
}