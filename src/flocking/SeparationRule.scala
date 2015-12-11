package flocking

import com.badlogic.gdx.math.Vector2

object SeparationRule {

  def separationVector(boid: Boid, neighbors: Seq[Boid], separation : Float): Vector2 = {
		val force = new Vector2
    for (neighbor <- neighbors) {
			val distance = distanceBetween(boid.position, neighbor.position)
			if (distance < separation) {
				val fix = Math.pow(1 - Math.min(distance, separation) / separation, 2f).toFloat
				val difference = new Vector2(boid.position).sub(neighbor.position).nor().scl(fix)
				val scaled = difference.scl(separation)
				force.add(scaled)
			}
    }
		force
  }

  def distanceBetween(from: Vector2, to: Vector2) = Vector2.dst(from.x, from.y, to.x, to.y)

}
