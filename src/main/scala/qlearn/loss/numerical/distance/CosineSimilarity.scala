package qlearn.loss.numerical.distance

import qlearn.Types.Vec

object CosineSimilarity extends Distance {

	def sim(a: Vec, b: Vec): Double =
		math.max(0.0, a.dot(b) / math.sqrt(a.dot(a) * b.dot(b)))

	def apply(a: Vec, b: Vec) = 1 - sim(a, b)
}
