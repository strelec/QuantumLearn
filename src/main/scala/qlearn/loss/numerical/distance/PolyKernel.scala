package qlearn.loss.numerical.distance

import breeze.linalg.sum
import qlearn.Types._

case class PolyKernel(exponent: Double) extends Distance {

	def apply(a: Vec, b: Vec) = math.pow(a.dot(b), exponent)

	override def apply(a: Mat, b: Vec) = a.r.dot(b) :^ exponent

	override def apply(a: Mat, b: Mat) = sum((a :* b).r) :^ exponent
}
