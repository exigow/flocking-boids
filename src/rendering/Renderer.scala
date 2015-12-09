package rendering

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.{Vector2, Matrix4}
import flocking.Boid
import org.lwjgl.opengl.GL11

object Renderer {

  private lazy val renderer = new ShapeRenderer()

  def render(projection: Matrix4, boids: Seq[Boid]): Unit = {
    clearBackground()
    renderer.setProjectionMatrix(projection)
    renderer.begin(ShapeRenderer.ShapeType.Line)
    for (boid <- boids) {
      renderArrow(boid.position.x, boid.position.y, boid.rotation)
      renderLineRelativeScaled(boid.position, boid.separation, 1)
      renderer.circle(boid.position.x, boid.position.y, 128)
    }
    renderer.end()
  }

  private def renderLineRelativeScaled(where: Vector2, plus: Vector2, scale: Float): Unit =
    renderer.line(where, new Vector2(where).add(new Vector2(plus).scl(scale)))

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
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f)
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT)
  }

}
