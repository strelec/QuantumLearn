package qlearn.loss.numerical.distance

import breeze.stats.mean
import qlearn.Types.Vec

case class PolyKernel(exponent: Double) extends Distance {

	def apply(a: Vec, b: Vec) = math.pow(a.dot(b), exponent)
}
