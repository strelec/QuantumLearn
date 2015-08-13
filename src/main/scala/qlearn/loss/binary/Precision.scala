package qlearn.loss.binary

import qlearn.dataset.Binary
import qlearn.loss.Loss

object Precision extends Loss[Binary] {

	val range = 1.0 -> 0.0

	def apply(actual: Binary, predicted: Binary) = {
		val tp = (actual.yt :& predicted.yt).activeSize.toDouble
		val fp = (!actual.yt :& predicted.yt).activeSize
		tp / (tp + fp)
	}
}