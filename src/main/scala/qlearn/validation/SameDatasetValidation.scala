package qlearn.validation

import qlearn.dataset.{Nominal, SingleLabeled}
import qlearn.ml.Model

case class SameDatasetValidation[T <: SingleLabeled[T]](data: T) extends Validation[T] {

	/*
		Simple validation on the same dataset which you learned from.

		Space complexity: O(1)
		Time complexity: O(1)
	 */
	
	def validate(model: Model[T]) = {
		val prediction = model.fit(data).predict(data.x)
		loss(data, prediction)
	}
}