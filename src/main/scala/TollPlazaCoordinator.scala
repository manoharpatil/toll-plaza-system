import akka.stream.Materializer
import akka.stream.scaladsl.Source

class TollPlazaCoordinator(implicit materializer: Materializer) {
  def coordinateTraffic(vehicles: Source[Vehicle, Any]): Unit = {
    val tollBooths = Seq(
      new TollBooth(1),
      new TollBooth(2),
      new TollBooth(3)
    )

    vehicles.runForeach(vehicle => {
      val selectedBooth = tollBooths.minBy(_.numVehicles)
      selectedBooth.processVehicle(vehicle)
    })
  }
}
