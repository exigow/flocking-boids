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
		val others = neighbors.filter(other => distanceBetween(boid.position, other.position) < maxDistance)
		if (others.isEmpty)
			return new Vector2
		val result = new Vector2
		var count : Int = 0
		for (other <- others) {
			result.add(other.position)
			count += 1
		}
		result.scl(1f / count.toFloat).add(-boid.position.x, -boid.position.y)
	}
	
	def normalizedInfluence(distance: Float, maxDistance: Float) =
		Math.pow(1 - Math.min(distance, maxDistance) / maxDistance, 2f).toFloat

  def distanceBetween(from: Vector2, to: Vector2) = Vector2.dst(from.x, from.y, to.x, to.y)

}
