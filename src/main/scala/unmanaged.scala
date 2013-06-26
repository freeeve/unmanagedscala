package unmanagedscala

import org.neo4j.graphdb.GraphDatabaseService

import javax.ws.rs._
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.MediaType

import scala.collection.JavaConverters._

import net.liftweb.json._
import net.liftweb.json.Extraction._

@Path("/service")
class unmanagedscala {

    implicit val formats = net.liftweb.json.DefaultFormats

    @GET
    @Path("/helloworld")
    def helloWorld = {
      "Hello World!"
    }

    @GET
    @Path("/fofallnodes")
    @Produces(Array("application/json"))
    def fof(@Context db:GraphDatabaseService) = {
       val map = collection.mutable.HashMap[String,Int]()
       for(node <- db.getAllNodes.asScala) {
         var count = 0
         for(r <- node.getRelationships.asScala) {
           val o = r.getOtherNode(node)
           for(r2 <- o.getRelationships.asScala) {
             if(!r.equals(r2)) count += 1
           }
         }
         map.put(node.getId.toString, count)
       }
       Response.ok(compact(render(decompose(map.toMap))), MediaType.APPLICATION_JSON).build()
    }

    @GET
    @Path("/fof/{id}")
    @Produces(Array("application/json"))
    def fof(@PathParam("id") id:Long, @Context db:GraphDatabaseService) = {
       val node = db.getNodeById(id)
       var count = 0
       for(r <- node.getRelationships.asScala) {
         val o = r.getOtherNode(node)
         for(r2 <- o.getRelationships.asScala) {
           count += 1
         }
       }
       Response.ok(compact(render(decompose(Map(id.toString -> count)))), MediaType.APPLICATION_JSON).build()
    }
}
