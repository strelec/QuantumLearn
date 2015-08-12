package qlearn.loss.numerical.distance

import breeze.linalg.sum
import breeze.numerics.abs
import qlearn.Types._

object SpearmanFootruleDistance extends Distance {

	def denom(k: Int) =
		if (k % 2 == 0) k*k/2.0 else (k+1)*(k-1)/2.0

	override def apply(a: Vec, b: Vec) =
		1 - sum(abs(a - b)) / denom(a.length)

	override def apply(a: Mat, b: Vec) =
		sum(abs(a.r - b).r) / -denom(a.cols) + 1.0

	override def apply(a: Mat, b: Mat) =
		sum(abs(a - b).r) / -denom(a.cols) + 1.0

	override def total(a: Mat, b: Mat) =
		a.rows - sum(abs(a - b)) / denom(a.cols)
}