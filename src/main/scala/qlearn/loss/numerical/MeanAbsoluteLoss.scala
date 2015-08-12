package qlearn.loss.numerical

import breeze.stats.mean
import breeze.numerics.abs
import qlearn.dataset.Numerical
import qlearn.loss.Loss

object MeanAbsoluteLoss extends Loss[Numerical] {

	def apply(actual: Numerical, predicted: Numerical) =
		 mean(abs(actual.y - predicted.y))
 }
