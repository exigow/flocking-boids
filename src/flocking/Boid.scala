package flocking

import com.badlogic.gdx.math.Vector2
import partitioning.SpatialPartitioning.Position

class Boid(val pos: Vector2, var rotation: Float, val separation: Vector2 = new Vector2, val cohesion: Vector2 = new Vector2, val alignment: Vector2 = new Vector2, var speed : Float = 0, val maxSpeed : Float = 8f) extends Position {

  override def position() = (pos.x, pos.y)

}
