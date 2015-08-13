package qlearn.loss.numerical

import breeze.stats.mean
import breeze.numerics.abs
import qlearn.dataset.Numerical
import qlearn.loss.Loss

object MeanAbsoluteLoss extends Loss[Numerical] {

	val range = 0.0 -> Double.PositiveInfinity

	def apply(actual: Numerical, predicted: Numerical) =
		 mean(abs(actual.y - predicted.y))
}
