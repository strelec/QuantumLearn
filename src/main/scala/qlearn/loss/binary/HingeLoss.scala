package qlearn.loss.binary

import breeze.stats.mean
import breeze.linalg.max
import qlearn.dataset.Binary
import qlearn.loss.Loss

object HingeLoss extends Loss[Binary] {
	def apply(actual: Binary, predicted: Binary) = {
		val m = -actual.ymat :* predicted.ymat + 1.0
		mean(max(m, 0.0))
	}
}