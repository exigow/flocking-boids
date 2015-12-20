package partitioning;

import java.util.List;

public class QuadTree {

  private final Quad bounds;
  private QuadTreeElement[] elements;

  private QuadTree topLeft;
  private QuadTree topRight;
  private QuadTree botLeft;
  private QuadTree botRight;

  public QuadTree(float size, int elemPerQuad) {
    this(0, 0, size, elemPerQuad);
  }

  public QuadTree(float x, float y, float size, int elemPerQuad) {
    bounds = new Quad(x, y, size);
    elements = new QuadTreeElement[elemPerQuad];
  }

  protected boolean set(QuadTreeElement e) {
    for (int i = 0; i < elements.length; i++) {
      if (elements[i] == null) {
        elements[i] = e;
        return true;
      }
    }
    return false;
  }

  public boolean insert(QuadTreeElement e) {
    if (!bounds.contains(e.positionX(), e.positionY())) {
      return false;
    }
    if (set(e)) {
      return true;
    } else {
      subdivide();
      if (topRight.insert(e)) return true;
      if (topLeft.insert(e)) return true;
      if (botRight.insert(e)) return true;
      if (botLeft.insert(e)) return true;

      throw new RuntimeException();
    }
  }

  public void query(Quad range, List<QuadTreeElement> results) {
    if (!areIntersecting(bounds, range))
      return;
    for (int i = 0; i < elements.length; i++) {
      if (elements[i] != null) {
        if (range.contains(elements[i].positionX(), elements[i].positionY())) {
          results.add(elements[i]);
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

  public boolean hasChildren() {
    return topLeft != null;
  }

  protected boolean subdivide() {
    if (hasChildren()) {
      return false;
    }
    float hs = bounds.size() / 2;
    topLeft  = new QuadTree(bounds.x(), bounds.y(), hs, elements.length);
    topRight = new QuadTree(bounds.x()+hs, bounds.y(), hs, elements.length);
    botLeft  = new QuadTree(bounds.x(), bounds.y()+hs, hs, elements.length);
    botRight = new QuadTree(bounds.x()+hs, bounds.y()+hs, hs, elements.length);
    return true;
  }

  private static boolean areIntersecting(Quad a, Quad b) {
    return (a.x() + a.size() > b.x()) && (a.y() + a.size() > b.y());
  }

}