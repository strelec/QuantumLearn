package qlearn.ml

import qlearn.dataset.{Labeled, Unlabeled}


abstract class FittedModel[T] {

	/*
		We keep the reference to the learning dataset just to know the schema
		(e.g. the ordinals of the nominal attributes, etc).

		TODO: get rid of this in the future. The learning dataset should be garbage collected ASAP.
	 */

	val schema: T

	/*
		The predict() method is the primary one. It returns the resulting dataset.
	 */

	def predict(data: Unlabeled): T
}
