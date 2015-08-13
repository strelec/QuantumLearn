package qlearn.loss.numerical.distance

import breeze.numerics.exp
import qlearn.Types._

case class RBFKernel(gamma: Double = 0.01) extends Distance {

	def apply(a: Vec, b: Vec) = {
		val dots = dot(a,b)*2.0 - dot(a,a) - dot(b,b)
		exp(dots * gamma)
	}

	override def apply(a: Mat, b: Vec) = {
		val dots = dot(a,b)*2.0 - dot(a,a) - dot(b,b)
		exp(dots * gamma)
	}

	override def apply(a: Mat, b: Mat) = {
		val dots = dot(a,b)*2.0 - dot(a,a) - dot(b,b)
		exp(dots * gamma)
	}
}