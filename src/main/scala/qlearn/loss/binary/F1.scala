package qlearn.loss.binary

import qlearn.dataset.Binary
import qlearn.loss.Loss

object F1 extends Loss[Binary] {

	def apply(actual: Binary, predicted: Binary) = {
		val tp = (actual.yt :& predicted.yt).activeSize.toDouble
		val fp = (!actual.yt :& predicted.yt).activeSize
		val fn = (actual.yt :& !predicted.yt).activeSize

		val precision = tp / (tp + fp)
		val recall = tp / (tp + fn)

		2 * precision * recall / (precision + recall)
	}
}