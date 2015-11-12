package qlearn.dataset

import qlearn.Types.Mat
import qlearn.loss.Loss
import qlearn.util.Util

case class MultiLabeled[+T <: SingleLabeled[T]](ys: T*) extends Labeled[MultiLabeled[T]] {
	require(ys.nonEmpty, "Specify at least one dataset.")

	val x = ys.head.x

	lazy val width = ys.map(_.width).sum

	lazy val ymat = Mat.horzcat(ys.map(_.ymat): _*)

	// TODO: Is this needed? How to do it?
	def loss = ???


	private val widths = ys.scanLeft(0)(_ + _.width)

	def updated(xnew: Unlabeled, ynew: Mat) =
		MultiLabeled(ys.zipWithIndex.map { case (y, i) =>
			val start = widths(i)
			val stop = widths(i + 1)
			val slice = ynew(::, start until stop)
			y.updated(xnew, slice)
		}: _*)


	override def toString = {
		val labels = ys.map(_.name.name) mkString ", "
		s"Multi(${x.labelString}, $labels)"
	}

	/*
		Function that writes the dataset to stdout
		*/

	lazy val reportHeader = ys.flatMap(_.reportHeader)

	def reportLine(line: Int) = ys.flatMap(_.reportLine(line))
}