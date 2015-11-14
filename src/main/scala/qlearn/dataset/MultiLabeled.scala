package qlearn.dataset

import qlearn.Types.Mat
import qlearn.dataset.schema.Column
import qlearn.loss.Loss
import qlearn.util.Util

case class MultiLabeled[T <: SingleLabeled[T]](ys: T*) extends Labeled[MultiLabeled[T]] {
	require(ys.nonEmpty, "Specify at least one dataset.")

	val x = ys.head.x

	lazy val width = ys.map(_.width).sum

	lazy val ymat = Mat.horzcat(ys.map(_.ymat): _*)

	type Tmp = T
	val schema = new Column {
		type T = MultiLabeled[Tmp]

		val name = 'MultiToBeRenamed

		val loss = new Loss[T] {
			val range = 1.0 -> 2.0

			def apply(a: T, b: T) = 1.0
				/*(a.ys, b.ys).zipped.map(
					(as, bs) => as.schema.loss(as, bs)
				).sum / a.ys.size*/
		}

		def populate(x: Unlabeled, y: Mat) =
			MultiLabeled(ys.zipWithIndex.map { case (y2, i) =>
				val start = widths(i)
				val stop = widths(i + 1)
				val slice = y(::, start until stop)
				y2.updated(x, slice)
			}: _*)
	}


	private val widths = ys.scanLeft(0)(_ + _.width)

	def updated(xnew: Unlabeled, ynew: Mat) = schema.populate(xnew, ynew)

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