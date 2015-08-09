package qlearn.loss.numerical.distance

import breeze.stats.mean
import qlearn.Types.Vec

case class RBFKernel(gamma: Double = 0.01) extends Distance {

	def apply(a: Vec, b: Vec) = {
		val dots = 2.0 * a.dot(b) - a.dot(a) - b.dot(b)
		math.exp(gamma * dots)
	}
}
