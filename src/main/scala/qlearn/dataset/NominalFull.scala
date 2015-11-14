package qlearn.dataset

import qlearn.Types._
import qlearn.dataset.schema.NominalColumn
import qlearn.util.Util

case class NominalFull(x: Unlabeled, ymat: Mat, schema: NominalColumn) extends Nominal {

	def values = schema.values

	def updated(xnew: Unlabeled, ynew: Mat) = schema.populate(xnew, ynew)


	/*
		Function that writes the dataset to stdout
		*/

	lazy val reportHeader =
		values.map(x =>
			"%9s" format s"${name.name}=$x"
		)

	def reportLine(line: Int) = {
		val vec = ymat(line, ::).inner.toScalaVector
		vec.zipWithIndex.map { case (v, i) =>
			Util.printDoubleNicely(v, reportHeader(i).size)
		}
	}
}
