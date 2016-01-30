package qlearn.validation

import qlearn.dataset.Labeled
import qlearn.ml.Model

case class LeaveOneOut[T <: Labeled[T]](data: T) extends Validation[T] {

	/*
		This validator mutates the dataset, therefore we have to make a defensive copy
		prior each validation. So, in case you are using multiple threads (or just want
		to trigger garbage collector less offently), use CrossValidation validator with
		folds = recordCount. It uses twice as much memory once on initialization, but
		then you can make as many validations as you need, even concurrently, without
		any additional memory usage.

		Space complexity: N * number of threads
		Time complexity: O(1) - no preprocessing
	 */

	def validate(model: Model[T]) = {
		val copy = data.duplicate(data.indices.init)

		def compare(excluded: T) = {
			val prediction = model.fit(copy).predict(excluded.x)
			loss(excluded, prediction)
		}

		val last = compare(data(-1))

		val init = copy.indices.map { i =>
			val excluded = data(i)

			copy.xmat(i, ::) := excluded.xmat(0, ::)
			copy.ymat(i, ::) := excluded.ymat(0, ::)

			compare(excluded)
		}.sum

		(init + last) / data.recordCount
	}
}
