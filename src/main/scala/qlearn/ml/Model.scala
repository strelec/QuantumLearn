package qlearn.ml

import qlearn.dataset.{MultiLabeled, SingleLabeled}

import scala.util.Random

abstract class Model[T <: SingleLabeled[T]] {

	/*
		Models that want to report their learning status back to the user
		can allow them to specify this function
	 */

	val reporter = { (_: Seq[Double], _: String) => /* no action */ }

	/*
		The fit() method is the primary one. It returns the fitted model.
	 */

	def fit(data: T): FittedModel[T]

	def fit(data: MultiLabeled[T]): FittedModel[MultiLabeled[T]] = FittedModelMulti(data, data.ys.map(fit))
}