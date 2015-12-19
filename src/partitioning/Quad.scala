package partitioning

class Quad(val x: Float, val y: Float, val size: Float) {

  def contains(x: Float, y: Float): Boolean = {
    if (x < this.x || y < this.y) return false
    if (x > this.x + size || y > this.y + size) return false
    true
  }

}
