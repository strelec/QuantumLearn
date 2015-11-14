package qlearn.dataset.schema

import qlearn.Types.Mat
import qlearn.dataset.{Binary, Unlabeled}
import qlearn.loss.binary.LogisticLoss

case class BinaryColumn(name: Symbol) extends Column {
	type T = Binary

	def populate(x: Unlabeled, y: Mat) = {
		assert(y.cols == 2)
		Binary(x, y(::, 1), this, LogisticLoss)
	}
}