package qlearn.loss.numerical.distance

import breeze.linalg.sum
import breeze.numerics.abs
import breeze.stats.mean
import qlearn.Types.{Mat, Vec}

object SpearmanFootruleDistance extends Distance {

	def denom(k: Int) =
		if (k % 2 == 0) k*k/2.0 else (k+1)*(k-1)/2.0

	def apply(a: Vec, b: Vec) =
		1 - sum(abs(a - b)) / denom(a.length)

	override def apply(a: Mat, b: Mat) =
		a.rows - sum(abs(a - b)) / denom(a.cols)
}