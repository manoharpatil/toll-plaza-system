### Toll Plazz System   

---

### Problem
Build a system in Scala with a detailed project structure and full implementation.

System Details:
- There are 3 gates at a traffic toll plaza and there are only 3 types of vehicles crossing this: bikes, cars, and trucks.

- Assume it takes exactly 10 seconds for any vehicle to cross the toll booth.

- The stretch of road after the toll plaza allows very high-speed driving. So within a 5-minute window, a maximum of 15% 
  of bikes, 25% of trucks, and 60% of cars approaching the toll plaza are to be allowed to pass through. A minimum of 
  15 bikes, 25 trucks, and 60 cars can be allowed to pass in any 5-minute window.

- If vehicles are allowed to come in any order into the toll plaza, they could create a bottleneck. Imagine a large 
  fleet of trucks approaching the plaza and blocking all the 3 gates due to the percentage rule. This will inhabit bikes 
  and cars from passing through.

- Goal of the program is to build a system that directs incoming vehicles into one of the booths - while trying to 
  optimize the traffic distribution on the road to be as close to the requisite percentage as possible. (You can imagine 
  yet another gate before the 3 booths which just directs incoming vehicles based on type to booth 1 or 2 or 3)

- Incoming traffic distribution can be hap-hazard. You can have only cars coming in for 30 minutes continuously.

- For computing purposes, you can model the vehicles as

      {

            type: ...              // car or bike or truck

            number: ...            // 'KA05-1234'

            ingress_timestamp: ... // epoch long number

            egress_timestamp: ...  // epoch long number

            egress_booth: ...      // 1 or 2 or 3

      }

- The final output of the program is to print to console/terminal the number of vehicles on the road past the plaza 
  every minute. 
  Example:

      <epoch timestamp for July 1, 2023, 9:00AM UTC>, X1, Y1, Z1

      <epoch timestamp for July 1, 2023, 9:01AM UTC>, X2, Y2, Z2

      ...

      where X1/X2 are num of cars, Y1/Y2 are num of bikes and Z1/Z2 are num of trucks

- You can assume incoming vehicle data to be coming on a Kafka topic with 'n' partitions. You are not allowed to use 
  any other external database or caches for this program.

- Assume this to be a business problem and use sensible defaults and assumptions. You can make things configurable when 
  you don't know the technical answer to something. But do not expect more info on this problem from the business folk.

---    
