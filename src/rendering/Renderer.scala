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
    renderGrid()
    /*renderer.setColor(1, .5f, .5f, 1)
    for (boid <- boids)
      renderRelativeVectorCross(boid.pos, boid.separation)
    renderer.setColor(.5f, 1, .5f, 1)
    for (boid <- boids)
      renderRelativeVectorCross(boid.pos, boid.cohesion)
    renderer.setColor(.5f, .5f, 1, 1)
    for (boid <- boids)
      renderRelativeVectorCross(boid.pos, boid.alignment)*/
    renderer.setColor(1, 1, 1, 1)
    for (boid <- boids)
      renderArrow(boid.pos.x, boid.pos.y, boid.rotation)
    renderer.end()
  }

  private def renderGrid(): Unit = {
    val max = 16
    val scale = 64
    for (x <- 0 to max) renderer.line(x * scale, 0, x * scale, scale * max)
    for (y <- 0 to max) renderer.line(0, y * scale, scale * max, y * scale)
  }

  private def renderRelativeVectorCross(position: Vector2, vector: Vector2): Unit = {
    val relative = new Vector2(position).add(new Vector2(vector))
    renderer.line(position.x, position.y, position.x + vector.x, position.y + vector.y)
    renderCross(relative)
  }

  private def renderCross(where: Vector2): Unit = {
    val size = 4
    renderer.line(where.x - size, where.y, where.x + size, where.y)
    renderer.line(where.x, where.y - size, where.x, where.y + size)
  }

  private def renderArrow(x: Float, y: Float, rotation: Float): Unit = {
    val arrowVertices : Array[Float] = Array(
      -1, -.5f,
      -.75f, 0,
      -1, .5f,
      1f, 0
    )
    val scaled = VertexArrayTransformations.scale(arrowVertices, 8)
    val rotated = VertexArrayTransformations.rotate(scaled, rotation)
    val transformed = VertexArrayTransformations.move(rotated, x, y)
    renderer.polygon(transformed)
  }

  private def clearBackground(): Unit = {
    Gdx.gl.glClearColor(.125f, .125f, .125f, 1f)
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT)
  }

}
