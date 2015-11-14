package qlearn.dataset.schema

import qlearn.Types.Mat
import qlearn.dataset.{Numerical, Unlabeled}
import qlearn.loss.numerical.MeanSquaredLoss

case class NumericalColumn(name: Symbol) extends Column {
	type T = Numerical

	def populate(x: Unlabeled, y: Mat) = {
		assert(y.cols == 1)
		Numerical(x, y(::, 0), this, MeanSquaredLoss)
	}
}