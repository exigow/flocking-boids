package flocking

import com.badlogic.gdx.math.Vector2

object SeparationRule {

  def separationVector(boid: Boid, neighbors: Seq[Boid], separation : Float = 128): Vector2 = {
		val force = new Vector2
		var count : Int = 0
    for (neighbor <- neighbors) {
			val distance = distanceBetween(boid.position, neighbor.position)
			if (distance < separation) {
				val difference = new Vector2(boid.position).sub(neighbor.position).nor().scl(1f / distance)
				force.add(difference)
				count += 1
			}
    }
		if (count > 0)
			force.scl(1f / count).scl(separation)
		force
  }

  def distanceBetween(from: Vector2, to: Vector2) = Vector2.dst(from.x, from.y, to.x, to.y)

  /*
  private function getSeparation( boids : Vector.<Boid>, separation : Number = 25.0 ) : Vector3D
		{
			var force : Vector3D = new Vector3D();
			var difference : Vector3D;
			var distance : Number;
			var count : int = 0;
			var boid : Boid;

			for (var i : int = 0;i < boids.length; i++)
			{
				boid = boids[i];

				distance = Vector3D.distance(_position, boid.position);

				if ( distance > 0 && distance < separation )
				{
					difference = _position.subtract(boid.position);
					difference.normalize();
					difference.scaleBy(1 / distance);

					force.incrementBy(difference);
					count++;
				}
			}

			if ( count > 0 )
			{
				force.scaleBy(1 / count);
			}

			return force;
		}
   */


}
