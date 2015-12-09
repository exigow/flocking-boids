package flocking

import com.badlogic.gdx.math.MathUtils._

class World {

  val boids = Seq.fill(10)(createRandomBoid)

  def update(): Unit = {
    for (boid <- boids) {
      boid.x += cos(boid.rotation) * .125f
      boid.y += sin(boid.rotation) * .125f
    }
  }

  private def createRandomBoid: Boid = {
    val size = 256f
    def randSize = random(-size, size)
    new Boid(randSize, randSize, random(PI))
  }

}
