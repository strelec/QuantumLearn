package qlearn.dataset

import breeze.linalg.argmax
import qlearn.Types._
import qlearn.dataset.schema.NominalColumn

case class NominalBasic(x: Unlabeled, override val y: IntVec, schema: NominalColumn) extends Nominal {

	def values = schema.values

	lazy val ymat =
		Mat.tabulate(y.size, values.size) { (r, c) =>
			if (y(r) == c) 1.0 else 0.0
		}

	def updated(xnew: Unlabeled, ynew: Mat) = schema.populate(xnew, ynew)

	def updatedSame(xnew: Unlabeled, ynew: Mat): NominalBasic = {
		assert(ynew.cols == values.size)
		copy(x = xnew, y = argmax(ynew.r))
	}

	/*
		Function that writes the dataset to stdout
	 */

	lazy val reportHeader = {
		val len = values.map(_.size).max
		Seq(s"%${len}s" format name.name)
	}

	def reportLine(line: Int) =
		Seq(values(y(line)).padTo(reportHeader.head.size, ' '))
}