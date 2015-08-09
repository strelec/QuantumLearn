package qlearn.loss.numerical

import breeze.stats.mean
import qlearn.dataset.Numerical
import qlearn.loss.Loss

object SquaredLoss extends Loss[Numerical] {
	def apply(actual: Numerical, predicted: Numerical) =
		mean((actual.y - predicted.y) :^ 2.0)
}