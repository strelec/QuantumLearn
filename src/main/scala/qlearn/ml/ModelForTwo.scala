package qlearn.ml

import qlearn.dataset.Labeled


trait ModelForTwo[T <: Labeled[T]] {

	/*
		This trait represents the model's ability to learn from the dataset,
		which is split in two parts. Of course every model is able to do that
		by just concatenating beforehand. However, you should only use this
		trait if such thing could be done effortlessly, without additional
		memory or CPU costs. Note, concatenation is an O(n) operation.

		Why is this good?

		For models that implement this, we can avoid copying the dataset K times
		on K-fold cross validation. It is a matter of simply splicing out the current
		test fold from the dataset, leaving the training dataset in two separate parts
		(well, except for the first and last fold).
 */

	def fit(data1: T, data2: T): FittedModel[T]


	/*def fit(data: T): FittedModel[T] =
		fit(data, data.pick(0 to -1))*/
}
