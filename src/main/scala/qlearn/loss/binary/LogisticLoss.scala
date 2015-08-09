package qlearn.loss.binary

import breeze.numerics.{exp, log}
import breeze.stats.mean
import qlearn.dataset.Binary
import qlearn.loss.Loss

object LogisticLoss extends Loss[Binary] {
	def apply(actual: Binary, predicted: Binary) =
		mean(log(exp(-actual.ymat :* predicted.ymat) + 1.0))
}
