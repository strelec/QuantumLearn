package qlearn.loss.numerical.distance

import breeze.linalg.max
import breeze.numerics.sqrt
import qlearn.Types._

object CosineSimilarity extends Distance {

	override val range = 0.0 -> 1.0

	def apply(a: Vec, b: Vec) = {
		val sim = dot(a,b) / sqrt(dot(a,a) * dot(b,b))
		1 - math.max(sim, 0.0)
	}

	override def apply(a: Mat, b: Vec) = {
		val sim = dot(a,b) / sqrt(dot(a,a) * dot(b,b))
		-max(sim, 0.0) + 1.0
	}

	override def apply(a: Mat, b: Mat) = {
		val sim = dot(a,b) / sqrt(dot(a,a) :* dot(b,b))
		-max(sim, 0.0) + 1.0
	}
}
