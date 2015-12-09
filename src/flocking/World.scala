package flocking

import java.lang.Math._

import com.badlogic.gdx.math.MathUtils.PI
import com.badlogic.gdx.math.MathUtils._
import com.badlogic.gdx.math.MathUtils.atan2
import com.badlogic.gdx.math.MathUtils.cos
import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.math.MathUtils.sin
import com.badlogic.gdx.math.{MathUtils, Vector2}

class World {

  val boids = Seq.fill(8)(createRandomBoid)

  def update(): Unit = {
    for (boid <- boids) {
      val others = boids.filterNot(elm => elm == boid)
      val separation = SeparationRule.separationVector(boid, others)
      boid.separation.set(separation)
      move(boid, new Vector2(boid.position).add(boid.separation))
    }
  }

  def move(boid : Boid, where: Vector2): Unit = {
    val acceleration = .0125f
    val directionToDesignation = pointDirection(where, boid.position)
    val distanceToDesignation = pointDistance(where, boid.position)
    val flySpeedTarget = Math.min(distanceToDesignation * .025f, boid.maxSpeed)
    boid.speed += (flySpeedTarget - boid.speed) * acceleration
    boid.rotation += angdiff(directionToDesignation, boid.rotation) * .0125f
    val vx = boid.speed * cos(boid.rotation)
    val vy = boid.speed * sin(boid.rotation)
    boid.position.add(vx, vy)
  }

  private def createRandomBoid: Boid = {
    val size = 1f
    def randSize = random(-size, size)
    val position = new Vector2(randSize, randSize)
    val direction = random(PI)
    new Boid(position, direction)
  }

  def pointDirection(a: Vector2, b: Vector2): Float = pointDirection(a.x, a.y, b.x, b.y)

  def pointDirection(ax: Float, ay: Float, bx: Float, by: Float): Float = atan2(ay - by, ax - bx)

  def pointDistance(a: Vector2, b: Vector2): Float = pointDistance(a.x, a.y, b.x, b.y)

  def pointDistance(ax: Float, ay: Float, bx: Float, by: Float): Float = sqrt(pow(bx - ax, 2) + pow(by - ay, 2)).toFloat

  def angdiff(angle0: Float, angle1: Float): Float = {
    val angle0d = angle0 * MathUtils.radiansToDegrees
    val angle1d = angle1 * MathUtils.radiansToDegrees
    (((((angle0d - angle1d) % 360) + 540) % 360) - 180) * MathUtils.degreesToRadians
  }

}
