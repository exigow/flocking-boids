package flocking

import com.badlogic.gdx.math.Vector2

object FlockingRules {

  def separationVector(boid: Boid, neighbors: Seq[Boid], maxDistance: Float): Vector2 = {
		val force = new Vector2
    for (neighbor <- neighbors) {
			val distance = distanceBetween(boid.position, neighbor.position)
			if (distance < maxDistance) {
				val fix = normalizedInfluence(distance, maxDistance)
				val difference = new Vector2(boid.position).sub(neighbor.position).nor().scl(fix)
				val scaled = difference.scl(maxDistance)
				force.add(scaled)
			}
    }
		force
  }
	
	def cohesionVector(boid: Boid, neighbors: Seq[Boid], maxDistance: Float): Vector2 = {
		var resultX : Float = 0
		var resultY : Float = 0
		var count : Int = 1
		for (neighbor <- neighbors) {
			val distance = distanceBetween(boid.position, neighbor.position)
			if (distance < maxDistance) {
				resultX += neighbor.position.x - boid.position.x
				resultY += neighbor.position.y - boid.position.y
				count += 1
			}
		}
		resultX /= count
		resultY /= count
		new Vector2(resultX, resultY)
	}
	
	def normalizedInfluence(distance: Float, maxDistance: Float) =
		Math.pow(1 - Math.min(distance, maxDistance) / maxDistance, 2f).toFloat

  def distanceBetween(from: Vector2, to: Vector2) = Vector2.dst(from.x, from.y, to.x, to.y)

}
