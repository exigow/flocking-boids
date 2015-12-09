package rendering

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import flocking.Boid
import org.lwjgl.opengl.GL11

object Renderer {

  private lazy val renderer = new ShapeRenderer()

  def render(projection: Matrix4, boids: Seq[Boid]): Unit = {
    clearBackground()
    renderer.setProjectionMatrix(projection)
    renderer.begin(ShapeRenderer.ShapeType.Line)
    for (boid <- boids)
      renderArrow(boid.x, boid.y, boid.rotation)
    renderer.end()
  }
  
  private def renderArrow(x: Float, y: Float, rotation: Float): Unit = {
    val arrowVertices : Array[Float] = Array(
      -8, -4,
      -6, 0,
      -8, 4,
      8, 0
    )
    val rotated = VertexArrayTransformations.rotate(arrowVertices, rotation)
    val transformed = VertexArrayTransformations.move(rotated, x, y)
    renderer.polygon(transformed)
  }

  private def clearBackground(): Unit = {
    Gdx.gl.glClearColor(.5f, .5f, .5f, 1f)
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT)
  }

}
