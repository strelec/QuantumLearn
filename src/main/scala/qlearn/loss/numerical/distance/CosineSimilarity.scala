package qlearn.loss.numerical.distance

import breeze.linalg.{max, sum}
import breeze.numerics.sqrt
import qlearn.Types._

object CosineSimilarity extends Distance {

	def apply(a: Vec, b: Vec) = {
		val sim = a.dot(b) / math.sqrt(a.dot(a) * b.dot(b))
		1 - math.max(sim, 0.0)
	}

	override def apply(a: Mat, b: Vec) = {
		val sim = a.r.dot(b) / sqrt(sum((a :* a).r) * b.dot(b))
		-max(sim, 0.0) + 1.0
	}

	override def apply(a: Mat, b: Mat) = {
		val sim = sum((a :* b).r) / sqrt(sum((a :* a).r) :* sum((b :* b).r))
		-max(sim, 0.0) + 1.0
	}
}
