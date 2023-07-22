import akka.actor.ActorSystem
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class VehicleProcessor(implicit materializer: Materializer) {
  def startProcessing(kafkaTopic: String, numPartitions: Int)(implicit system: ActorSystem): Unit = {
    val consumerSettings: ConsumerSettings[String, String] = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("toll-plaza-consumer-group")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    val tollPlazaCoordinator = new TollPlazaCoordinator()

    Consumer.plainSource(consumerSettings, Subscriptions.topics(kafkaTopic))
      .map(record => processKafkaRecord(record))
      .grouped(numPartitions) // Grouping to handle multiple partitions in parallel
      .map(records => Source(records))
      .mapAsync(numPartitions)(source => Future(tollPlazaCoordinator.coordinateTraffic(source)))
      .runWith(Sink.ignore)
  }

  private def processKafkaRecord(record: ConsumerRecord[String, String]): Vehicle = {
    // Parse and create the Vehicle instance from the Kafka record
    // Implement the logic to handle epoch timestamps and other fields as needed
    val vehicleJsonString = record.value()
    val vehicleJson = vehicleJsonString.parseJson

    vehicleJson.asJsObject.getFields("type", "number", "ingress_timestamp", "egress_timestamp", "egress_booth") match {
      case Seq(JsString(vehicleType), JsString(number), JsNumber(ingressTimestamp), JsNumber(egressTimestamp), JsNumber(egressBooth)) =>
        Vehicle(vehicleType, number, ingressTimestamp.toLong, egressTimestamp.toLong, egressBooth.toInt)
      case _ =>
        throw new IllegalArgumentException(s"Invalid JSON format for vehicle record: $vehicleJsonString")
    }
  }
}