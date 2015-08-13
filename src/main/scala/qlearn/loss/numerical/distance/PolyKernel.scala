package qlearn.loss.numerical.distance

import breeze.numerics.pow
import qlearn.Types._

case class PolyKernel(exponent: Double) extends Distance {

	def apply(a: Vec, b: Vec) = pow(dot(a,b), exponent)

	override def apply(a: Mat, b: Vec) = pow(dot(a,b), exponent)

	override def apply(a: Mat, b: Mat) = pow(dot(a,b), exponent)
}
