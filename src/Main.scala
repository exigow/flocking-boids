import com.badlogic.gdx.math.{Vector2, Vector3}
import com.badlogic.gdx.{Gdx, ApplicationAdapter}
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.badlogic.gdx.graphics.OrthographicCamera
import flocking.World
import rendering.Renderer

object Main {

  def main(args: Array[String]): Unit = {
    val configuration = new LwjglApplicationConfiguration {
      title = "main"
      width = 1024
      height = 1024
      samples = 8
    }
    new LwjglApplication(new Application, configuration)
  }

  private class Application extends ApplicationAdapter {

    val world = new World

    lazy val camera = new OrthographicCamera() {
      setToOrtho(true, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
      position.set(0, 0, 0)
    }

    override def render(): Unit = {
      camera.update()
      val mouse3 = camera.unproject(new Vector3(Gdx.input.getX, Gdx.input.getY, 0))
      world.boids.head.position.set(new Vector2(mouse3.x, mouse3.y))
      world.update()
      Renderer.render(camera.combined, world.boids)
    }

  }


}
