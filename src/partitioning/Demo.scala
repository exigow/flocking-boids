package partitioning

import scala.collection.mutable.ListBuffer

object Demo {

  def main(args: Array[String]): Unit = {
    /*val tree = new QuadTree(512, 1)
    tree.insert(new Position(200, 200))
    tree.insert(new Position(300, 300))

    val list: util.List[QuadTreeElement] = new util.ArrayList[QuadTreeElement]()
    tree.query(new Quad(192, 192, 128), list)
    println(list)*/

    val tree = new Tree(new Quad(0, 0, 512))
    tree.insert(new Position(200, 200))
    tree.insert(new Position(300, 300))

    val list = new ListBuffer[QuadTreeElement]()
    tree.query(new Quad(256, 256, 128), list)
    println(list)
  }

  class Position(px: Float, py: Float) extends QuadTreeElement {

    override def x(): Float = px

    override def y(): Float = py

  }

}
