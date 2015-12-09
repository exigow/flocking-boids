import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils.{PI, random}
import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import models.Boid
import rendering.Renderer

class Application extends ApplicationAdapter {

  private lazy val camera = new OrthographicCamera() {
    setToOrtho(true, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    position.set(0, 0, 0)
  }
  private val boids = Seq.fill(10)(createRandomBoid)

  override def render(): Unit = {
    camera.update()
    Renderer.render(camera.combined, boids)
  }

  private def createRandomBoid: Boid = {
    val size = 256f
    def randSize = random(-size, size)
    new Boid(randSize, randSize, random(PI))
  }

}
