import scala.concurrent.Await
import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.stream.Materializer

class TollPlazaApp {
  def run(): Unit = {
    implicit val system: ActorSystem = ActorSystem("TollPlazaSystem")
    implicit val materializer: Materializer = Materializer(system)

    val kafkaTopic = "incoming-vehicles-topic"
    val numPartitions = 3

    val vehicleProcessor = new VehicleProcessor()
    vehicleProcessor.startProcessing(kafkaTopic, numPartitions)

    Await.result(system.whenTerminated, Duration.Inf)
  }
}
