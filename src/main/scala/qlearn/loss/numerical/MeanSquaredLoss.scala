package qlearn.loss.numerical

import breeze.stats.mean
import qlearn.dataset.Numerical
import qlearn.loss.Loss

object MeanSquaredLoss extends Loss[Numerical] {

	val range = 0.0 -> Double.PositiveInfinity

	def apply(actual: Numerical, predicted: Numerical) =
		mean((actual.y - predicted.y) :^ 2.0)
}