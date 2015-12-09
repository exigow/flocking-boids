package flocking

import com.badlogic.gdx.math.Vector2

class Boid(val position: Vector2, var rotation: Float, val separation: Vector2 = new Vector2, var speed : Float = 0, val maxSpeed : Float = 8f)
