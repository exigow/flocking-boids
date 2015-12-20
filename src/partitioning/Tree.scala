package partitioning

import scala.collection.mutable.ListBuffer

class Tree(val bounds: Quad) {

  private val elements = new ListBuffer[QuadTreeElement]()
  private var topLeft: Tree = null
  private var topRight: Tree = null
  private var botLeft: Tree = null
  private var botRight: Tree = null


}
