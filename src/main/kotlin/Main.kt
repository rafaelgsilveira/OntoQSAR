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
    embeddedServer(Netty, System.getenv("PORT")?.toInt() ?: 8080) {
        routing {
            trace { application.log.trace(it.buildText()) }
            
            
            static("/") {
                resource("/", "index2.html")
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
    val content = this::class.java.classLoader.getResourceAsStream("OntoQSAR4_data.ttl")
    read(content, null, "TTL")
}


fun executeQuery(query: String): String {
    val ontQuery = QueryFactory.create(query)
    val queryExec = QueryExecutionFactory.create(ontQuery, ontModel)
    val results = queryExec.execSelect()
    return results.asSequence().toList().joinToString()
}

