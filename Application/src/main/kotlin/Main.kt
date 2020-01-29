package me.kerooker.jena

import io.ktor.application.call
import io.ktor.application.log
import io.ktor.http.ContentType
import io.ktor.http.content.*
import io.ktor.http.defaultForFile
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.apache.jena.ontology.OntModelSpec
import org.apache.jena.query.QueryExecutionFactory
import org.apache.jena.query.QueryFactory
import org.apache.jena.rdf.model.ModelFactory
import java.io.File


fun main() {
    embeddedServer(Netty, 8080) {
        routing {
            trace { application.log.trace(it.buildText()) }

           get("/"){
               val html = File("/home/ragasil/Projects/01_OntoQSAR/WebSemantica2019-master/src/main/resources/index2.html").readText()
               call.respondText(html, ContentType.Text.Html)
           }

            static("static") {
                resources("static")
            }





            get("/query") {
                val query = call.request.queryParameters["query"]!!
                call.respondText(executeQuery(query))
            }
        }
    }.start(true)
}

val ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM).apply {
    read("/home/ragasil/Projects/01_OntoQSAR/WebSemantica2019-master/src/main/resources/OntoQSAR4_data.ttl")
}


fun executeQuery(query: String): String {
    val ontQuery = QueryFactory.create(query)
    val queryExec = QueryExecutionFactory.create(ontQuery, ontModel)
    val results = queryExec.execSelect()
    return results.asSequence().toList().joinToString()
}

