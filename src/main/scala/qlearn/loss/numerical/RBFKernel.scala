package qlearn.loss.numerical

import qlearn.dataset.Numerical
import qlearn.loss.Loss

case class RBFKernel(gamma: Double) extends Loss[Numerical] {
	def apply(a: Numerical, b: Numerical) = {
		val dots = 2.0 * a.y.dot(b.y) - a.y.dot(a.y) - b.y.dot(b.y)
		math.exp(gamma * dots)
	}
}
