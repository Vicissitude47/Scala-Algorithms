/**
 * @see http://algs4.cs.princeton.edu/15uf/UF.java.html
 */
package org.gs.set

/**
 * @author Gary Struthers
 * @param n number of sites
 */
class UF(n: Int) {
  require(n >= 0)
  private var _count = n
  private val id = Array.range(0, n)
  private val rank = Array.fill[Byte](n)(0)

  def count(): Int = _count

  def find(p: Int): Int = {
    require(p >= 0 && p < id.length)
    def loop(pP: Int): Int =
      if (pP != id(pP)) {
        id(pP) = id(id(pP))
        loop(id(pP))
      } else pP
    loop(p)
  }

  def connected(p: Int, q: Int): Boolean = find(p) == find(q)

  def union(p: Int, q: Int): Unit = {
    val i = find(p)
    val j = find(q)
    if (!(i == j)) {
      if (rank(i) < rank(j)) id(i) = j else if (rank(i) > rank(j)) id(j) = i else {
        id(j) = i
        rank(i) = (rank(i) + 1).toByte
      }
      _count = _count - 1
    }
  }
}
