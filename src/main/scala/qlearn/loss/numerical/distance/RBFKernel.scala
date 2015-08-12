package qlearn.loss.numerical.distance

import breeze.linalg.sum
import breeze.numerics.exp
import qlearn.Types._

case class RBFKernel(gamma: Double = 0.01) extends Distance {

	def apply(a: Vec, b: Vec) = {
		val dots = a.dot(b)*2.0 - a.dot(a) - b.dot(b)
		math.exp(dots * gamma)
	}

	override def apply(a: Mat, b: Vec) = {
		val dots = a.r.dot(b)*2.0 - sum((a :* a).r) - b.dot(b)
		exp(dots * gamma)
	}

	override def apply(a: Mat, b: Mat) = {
		val dots = sum((a :* b).r)*2.0 - sum((a :* a).r) - sum((b :* b).r)
		exp(dots * gamma)
	}
}