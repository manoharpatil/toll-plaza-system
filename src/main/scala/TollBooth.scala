import scala.collection.mutable

class TollBooth(val id: Int) {
  private val vehiclesOnRoad: mutable.Set[Vehicle] = mutable.Set.empty[Vehicle]

  def numVehicles: Int = vehiclesOnRoad.size

  def processVehicle(vehicle: Vehicle): Unit = {
    vehiclesOnRoad += vehicle
  }

  // Define other methods as needed
}
