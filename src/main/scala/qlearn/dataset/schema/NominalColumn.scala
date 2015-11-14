package qlearn.dataset.schema

import qlearn.Types.Mat
import qlearn.dataset.{NominalFull, Nominal, Unlabeled}
import qlearn.loss.Loss
import qlearn.loss.nominal.CrossEntropyLoss

case class NominalColumn(name: Symbol, values: Vector[String], loss: Loss[Nominal] = CrossEntropyLoss()) extends Column {
	type T = Nominal

	val lookup = values.zipWithIndex.toMap

	def populate(x: Unlabeled, y: Mat) = {
		assert(y.cols == values.size)
		NominalFull(x, y, this)
	}
}