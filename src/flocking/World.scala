package flocking

import java.lang.Math._

import com.badlogic.gdx.math.MathUtils.{PI, atan2, cos, random, sin}
import com.badlogic.gdx.math.{MathUtils, Vector2}
import partitioning.SpatialPartitioning

class World {

  val boids = Seq.fill(256)(createRandomBoid())

  def update(): Unit = {
    val partitionedSpace = SpatialPartitioning.partition(boids)
    for (boid <- boids) {
      val others = partitionedSpace.neighborsOf(boid).asInstanceOf[Seq[Boid]]
      val radius = 64
      val separation = FlockingRules.separationVector(boid, others, radius)
      val cohesion = FlockingRules.cohesionVector(boid, others, radius)
      val alignment = FlockingRules.alignmentVector(boid, others, radius)
      boid.separation.set(separation)
      boid.cohesion.set(cohesion)
      boid.alignment.set(alignment)
      val combo = new Vector2().add(separation).add(cohesion).add(alignment)
      move(boid, new Vector2(boid.pos).add(combo))
      applyWorldBordersLoop(boid)
    }
  }

  private def applyWorldBordersLoop(boid: Boid): Unit = {
    val max = 1024
    if (boid.pos.x > max)
      boid.pos.x -= max
    if (boid.pos.x < 0)
      boid.pos.x += max
    if (boid.pos.y > max)
      boid.pos.y -= max
    if (boid.pos.y < 0)
      boid.pos.y += max
  }

  private def move(boid : Boid, where: Vector2): Unit = {
    val acceleration = .0125f
    val directionToDesignation = pointDirection(where, boid.pos)
    val distanceToDesignation = pointDistance(where, boid.pos)
    val flySpeedTarget = Math.min(0 + distanceToDesignation * .025f, boid.maxSpeed)
    boid.speed += (flySpeedTarget - boid.speed) * acceleration
    boid.rotation += angdiff(directionToDesignation, boid.rotation) * .0125f
    val vx = boid.speed * cos(boid.rotation)
    val vy = boid.speed * sin(boid.rotation)
    boid.pos.add(vx, vy)
  }

  private def createRandomBoid(): Boid = {
    val size = 1024
    def randSize = random(size)
    val position = new Vector2(randSize, randSize)
    val direction = random(PI * 2)
    new Boid(position, direction)
  }

  private def pointDirection(a: Vector2, b: Vector2) = atan2(a.y - b.y, a.x - b.x)

  private def pointDistance(a: Vector2, b: Vector2) = sqrt(pow(b.x - a.x, 2) + pow(b.y - a.y, 2)).toFloat

  private def angdiff(angle0: Float, angle1: Float): Float = {
    val angle0d = angle0 * MathUtils.radiansToDegrees
    val angle1d = angle1 * MathUtils.radiansToDegrees
    (((((angle0d - angle1d) % 360) + 540) % 360) - 180) * MathUtils.degreesToRadians
  }

}
