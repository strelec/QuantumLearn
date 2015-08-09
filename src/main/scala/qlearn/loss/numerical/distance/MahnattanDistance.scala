package qlearn.loss.numerical.distance

import breeze.linalg.sum
import breeze.numerics.abs
import qlearn.Types._

object MahnattanDistance extends Distance {

	def apply(a: Vec, b: Vec) = sum(abs(a - b))

	override def apply(a: Mat, b: Vec) = sum(abs(a.r - b).r)

	override def apply(a: Mat, b: Mat) = sum(abs(a - b))
}
