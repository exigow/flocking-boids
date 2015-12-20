package partitioning

import java.util

object Demo {

  def main(args: Array[String]): Unit = {
    val tree = new QuadTree(512, 1)
    tree.insert(new Position(200, 200))
    tree.insert(new Position(300, 300))

    val list: util.List[QuadTreeElement] = new util.ArrayList[QuadTreeElement]()
    tree.query(new Quad(192, 192, 128), list)
    println(list)
  }

  class Position(px: Float, py: Float) extends QuadTreeElement {

    override def x(): Float = px

    override def y(): Float = py

  }

}
