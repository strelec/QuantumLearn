package qlearn.loss.numerical.distance

import breeze.linalg.{sum, max}
import breeze.numerics.abs
import qlearn.Types._

object ChebyshevDistance extends Distance {

	def apply(a: Vec, b: Vec) = max(abs(a - b))

	override def apply(a: Mat, b: Vec) =
		max((a.r - b).r)

	override def apply(a: Mat, b: Mat) =
		max(abs(a - b).r)
}
