package qlearn.ml

import scala.util.Random

trait RandomizedModel[T] extends Model[T] {
	/*
		A model should include this trait if its output significantly
		depends on the behavior of the random number generator.
	 */

	def fit(data: T, seed: Long): FittedModel[T]

	def fit(data: T) = fit(data, Random.nextLong)
}