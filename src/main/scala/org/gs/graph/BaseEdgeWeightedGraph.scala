/**
 * Common code for EdgeWeightedGraph, EdgeWeightedDigraph
 */
package org.gs.graph

import scala.collection.mutable.ListBuffer

/**
 * @author Gary Struthers
 *
 * @param <T> Edge, DirectedEdge
 * @param v number of vertices in EdgeWeightedGraph, EdgeWeightedDigraph
 */
abstract class BaseEdgeWeightedGraph[A <: BaseEdge](val v: Int) {
  require(v >= 0, s"Number of vertices, v:$v must be nonnegative")
  var e = 0
  val adj = Array.fill[List[A]](v)(List[A]())

  protected def buildADJ[U <: BaseEdgeWeightedGraph[A]](g: U): Unit = {
    e = g.e

    for {
      v <- 0 until g.v
      reverse = ListBuffer[A]()
    } {
      for {
        e <- g.adj(v)
      } reverse.prepend(e)
      for {
        er <- reverse
      } adj(v) = er :: adj(v)
    }
  }

  def rangeGuard(x: Int): Boolean = {
    x match {
      case x if 0 until v contains x => true
      case _ => false
    }
  }

  def edgesOnVertex(eV: Int): Seq[A] = {
    require(rangeGuard(eV), s"verticies eV:$eV  not in 0..$v ")
    adj(eV).toSeq
  }

  def edges():Seq[A]

  override def toString(): String = {
    val lf = sys.props("line.separator")
    val sb = new StringBuilder()
    sb.append(s"$v $e $lf")
    def addLines(vV: Int) {
      sb.append(s"$vV : ")
      for {
        ed <- adj(vV)
      } sb.append(s"$ed  ")
      sb.append(lf)
    }
    for {
      vV <- 0 until v
    } addLines(vV)
    sb.toString
  }
}