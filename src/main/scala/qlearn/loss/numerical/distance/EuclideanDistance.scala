package qlearn.loss.numerical.distance

import breeze.linalg.norm
import qlearn.Types._

object EuclideanDistance extends Distance {

	def apply(a: Vec, b: Vec) = norm(a - b)

	override def apply(a: Mat, b: Vec) =
		norm((a.r - b).r)

	override def apply(a: Mat, b: Mat) =
		norm((a - b).r)
}
