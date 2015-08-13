package qlearn.loss.binary

import qlearn.dataset.Binary
import qlearn.loss.Loss

object Recall extends Loss[Binary] {

	def apply(actual: Binary, predicted: Binary) = {
		val tp = (actual.yt :& predicted.yt).activeSize.toDouble
		val fn = (actual.yt :& !predicted.yt).activeSize
		tp / (tp + fn)
	}
}