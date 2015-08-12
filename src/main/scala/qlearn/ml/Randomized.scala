package qlearn.ml

import scala.util.Random

trait Randomized {
	/*
		A model should include this trait if its output significantly
		depends on the behavior of the random number generator.
	 */

	val seed: Long

	def getRandom = new Random(seed)
}