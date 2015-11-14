package qlearn.dataset.schema

import qlearn.Types.Mat
import qlearn.dataset.{Binary, Unlabeled}
import qlearn.loss.Loss
import qlearn.loss.binary.LogisticLoss

case class BinaryColumn(name: Symbol, loss: Loss[Binary] = LogisticLoss) extends Column {
	type T = Binary

	def populate(x: Unlabeled, y: Mat) = {
		assert(y.cols == 2)
		Binary(x, y(::, 1), this)
	}
}