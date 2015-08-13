package qlearn.loss.numerical.distance

import breeze.linalg.norm
import qlearn.Types._

case class NormDistance(p: Double) extends Distance {
	require(p >= 1.0, "P under 1.0 produces a degenerate norm.")

	def apply(a: Vec, b: Vec) = norm(a - b, p)

	override def apply(a: Mat, b: Vec) =
		norm((a.r - b).r, p)

	override def apply(a: Mat, b: Mat) =
		norm((a - b).r, p)
}