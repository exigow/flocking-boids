package partitioning

import scala.collection.mutable.ListBuffer

object Demo {

  def main(args: Array[String]): Unit = {
    val tree = new Tree(new Quad(0, 0, 512))
    tree.insert(new Position(200, 200))
    tree.insert(new Position(300, 300))

    val list = new ListBuffer[QuadTreeElement]()
    tree.query(new Quad(128, 128, 256), list)
    println(list)
  }

  class Position(x: Float, y: Float) extends QuadTreeElement {

    override def position = (x, y)

  }

}
