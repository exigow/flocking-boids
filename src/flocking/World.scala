package flocking

import com.badlogic.gdx.math.MathUtils._

class World {

  val boids: Seq[Boid] = Seq.fill(10)(createRandomBoid)

  def update(): Unit = {

  }

  private def createRandomBoid: Boid = {
    val size = 256f
    def randSize = random(-size, size)
    new Boid(randSize, randSize, random(PI))
  }

}
