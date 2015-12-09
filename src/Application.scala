import java.lang.Math.{sin, cos}

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import org.lwjgl.opengl.GL11

class Application extends ApplicationAdapter {

  private lazy val renderer = new ShapeRenderer()
  private lazy val camera = new OrthographicCamera() {
    setToOrtho(true, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    position.set(0, 0, 0)
  }
  val arrowVertices : Array[Float] = Array(
    -32, -16,
    -24, 0,
    -32, 16,
    32, 0
  )
  override def create(): Unit = {
  }

  override def render(): Unit = {
    camera.update()
    clearBackground()
    renderer.setProjectionMatrix(camera.combined)
    renderer.begin(ShapeRenderer.ShapeType.Line)
    renderer.polygon(arrowVertices)
    renderer.polygon(VertexArrayTransformations.move(VertexArrayTransformations.rotate(arrowVertices, .5f), 32, 64))
    renderer.end()
  }

  def clearBackground(): Unit = {
    Gdx.gl.glClearColor(.5f, .5f, .5f, 1f)
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT)
  }

}
