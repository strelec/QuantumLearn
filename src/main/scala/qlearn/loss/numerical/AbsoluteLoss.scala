package qlearn.loss.numerical

import breeze.stats.mean
import breeze.numerics.abs
import qlearn.dataset.Numerical
import qlearn.loss.Loss

object AbsoluteLoss extends Loss[Numerical] {
	def apply(actual: Numerical, predicted: Numerical) =
		 mean(abs(actual.y - predicted.y))
 }
