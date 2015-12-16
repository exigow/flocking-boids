package flocking

import java.lang.Math._

import com.badlogic.gdx.math.MathUtils.{PI, atan2, cos, random, sin}
import com.badlogic.gdx.math.{MathUtils, Vector2}

class World {

  val boids = Seq.fill(64)(createRandomBoid)

  def update(): Unit = {
    for (boid <- boids) {
      val others = boids.filterNot(tested => tested == boid)
      val separation = FlockingRules.separationVector(boid, others, 64)
      val cohesion = FlockingRules.cohesionVector(boid, others, 64)
      val alignment = FlockingRules.alignmentVector(boid, others, 64)
      boid.separation.set(separation)
      boid.cohesion.set(cohesion)
      boid.alignment.set(alignment)
      val combo = new Vector2().add(separation).add(cohesion).add(alignment)
      move(boid, new Vector2(boid.position).add(combo))
    }

    val max = 512
    for (boid <- boids) {
      if (boid.position.x > max)
        boid.position.x -= max * 2
      if (boid.position.x < -max)
        boid.position.x += max * 2
      if (boid.position.y > max)
        boid.position.y -= max * 2
      if (boid.position.y < -max)
        boid.position.y += max * 2
    }
  }

  def move(boid : Boid, where: Vector2): Unit = {
    val acceleration = .0125f
    val directionToDesignation = pointDirection(where, boid.position)
    val distanceToDesignation = pointDistance(where, boid.position)
    val flySpeedTarget = Math.min(.25f + distanceToDesignation * .025f, boid.maxSpeed)
    boid.speed += (flySpeedTarget - boid.speed) * acceleration
    boid.rotation += angdiff(directionToDesignation, boid.rotation) * .0125f
    val vx = boid.speed * cos(boid.rotation)
    val vy = boid.speed * sin(boid.rotation)
    boid.position.add(vx, vy)
  }

  private def createRandomBoid: Boid = {
    val size = 512
    def randSize = random(-size, size)
    val position = new Vector2(randSize, randSize)
    val direction = random(PI)
    new Boid(position, direction)
  }

  def pointDirection(a: Vector2, b: Vector2) = atan2(a.y - b.y, a.x - b.x)

  def pointDistance(a: Vector2, b: Vector2) = sqrt(pow(b.x - a.x, 2) + pow(b.y - a.y, 2)).toFloat

  def angdiff(angle0: Float, angle1: Float): Float = {
    val angle0d = angle0 * MathUtils.radiansToDegrees
    val angle1d = angle1 * MathUtils.radiansToDegrees
    (((((angle0d - angle1d) % 360) + 540) % 360) - 180) * MathUtils.degreesToRadians
  }

}
