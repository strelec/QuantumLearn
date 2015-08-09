package qlearn.loss.numerical.distance

import breeze.linalg.{norm, sum}
import qlearn.Types._

object EuclideanDistance extends Distance {

	def apply(a: Vec, b: Vec) = norm(a - b)

	override def apply(a: Mat, b: Vec) =
		norm((a.r - b).r)

	override def apply(a: Mat, b: Mat) =
		sum(norm((a - b).r))
}