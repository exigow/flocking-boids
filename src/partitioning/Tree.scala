package partitioning

import scala.collection.mutable.ListBuffer

class Tree(val bounds: Quad, elemPerQuad: Int = 1) {

  private val elements = new ListBuffer[QuadTreeElement]()
  private var topLeft: Tree = null
  private var topRight: Tree = null
  private var botLeft: Tree = null
  private var botRight: Tree = null

  private def subdivide(): Boolean = {
    if (hasChildren) return false
    val hs = bounds.size / 2
    topLeft  = new Tree(new Quad(bounds.x, bounds.y, hs), elemPerQuad)
    topRight = new Tree(new Quad(bounds.x + hs, bounds.y, hs), elemPerQuad)
    botLeft  = new Tree(new Quad(bounds.x, bounds.y + hs, hs), elemPerQuad)
    botRight = new Tree(new Quad(bounds.x + hs, bounds.y + hs, hs), elemPerQuad)
    true
  }

  /*
   public void query(Quad range, List<QuadTreeElement> results) {
    if (!areIntersecting(bounds, range))
      return;
    for (QuadTreeElement element : elements) {
      if (element != null) {
        if (range.contains(element.x(), element.y())) {
          results.add(element);
        }
      }
    }
    if (!hasChildren()) {
      return;
    }
    topLeft.query(range, results);
    topRight.query(range, results);
    botLeft.query(range, results);
    botRight.query(range, results);
  }
   */

  def query(range: Quad, results: ListBuffer[QuadTreeElement]): Unit = {
    if (!areIntersecting(bounds, range)) return
    for (element <- elements)
      if (range.contains(element.x, element.y))
        results += element
    if (!hasChildren) return
    topLeft.query(range, results)
    topRight.query(range, results)
    botLeft.query(range, results)
    botRight.query(range, results)
  }

  private def areIntersecting(a: Quad, b: Quad) = (a.x + a.size > b.x) && (a.y + a.size > b.y)

  def insert(element: QuadTreeElement): Boolean = {
    if (!bounds.contains(element.x, element.y)) return false
    if (elements.size < elemPerQuad) {
      elements += element
      return true
    }
    subdivide()
    if (topRight.insert(element)) return true
    if (topLeft.insert(element)) return true
    if (botRight.insert(element)) return true
    if (botLeft.insert(element)) return true
    throw new RuntimeException
  }

  def hasChildren: Boolean = topLeft != null

}
