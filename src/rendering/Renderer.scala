package rendering

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.{Matrix4, Vector2}
import flocking.Boid
import org.lwjgl.opengl.GL11

object Renderer {

  private lazy val renderer = new ShapeRenderer()

  def render(projection: Matrix4, boids: Seq[Boid]): Unit = {
    clearBackground()
    renderer.setProjectionMatrix(projection)
    renderer.begin(ShapeRenderer.ShapeType.Line)
    renderer.setColor(.25f, .25f, .25f, 1)
    for (boid <- boids)
      renderer.circle(boid.position.x, boid.position.y, 128)
    renderer.setColor(1, .5f, .5f, 1)
    for (boid <- boids)
      renderRelativeVectorCross(boid.position, boid.separation)
    renderer.setColor(.5f, 1, .5f, 1)
    for (boid <- boids)
      renderRelativeVectorCross(boid.position, boid.cohesion)
    renderer.setColor(1, 1, 1, 1)
    for (boid <- boids)
      renderArrow(boid.position.x, boid.position.y, boid.rotation)
    renderer.end()
  }

  private def renderRelativeVectorCross(position: Vector2, vector: Vector2): Unit = {
    val relative = new Vector2(position).add(new Vector2(vector))
    renderer.line(position.x, position.y, position.x + vector.x, position.y + vector.y)
    renderCross(relative)
  }

  private def renderCross(where: Vector2): Unit = {
    renderer.line(where.x - 8, where.y, where.x + 8, where.y)
    renderer.line(where.x, where.y - 8, where.x, where.y + 8)
  }

  private def renderArrow(x: Float, y: Float, rotation: Float): Unit = {
    val arrowVertices : Array[Float] = Array(
      -16, -8,
      -12, 0,
      -16, 8,
      16, 0
    )
    val rotated = VertexArrayTransformations.rotate(arrowVertices, rotation)
    val transformed = VertexArrayTransformations.move(rotated, x, y)
    renderer.polygon(transformed)
  }

  private def clearBackground(): Unit = {
    Gdx.gl.glClearColor(.125f, .125f, .125f, 1f)
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT)
  }

}
