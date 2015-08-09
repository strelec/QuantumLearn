package qlearn.ml.classify

import qlearn.Types.Mat
import qlearn.dataset.{Nominal, Unlabeled}
import qlearn.loss.numerical.distance.{EuclideanDistance, Distance}
import qlearn.ml.FittedModel

case class FittedKNN(
	schema: Nominal,
	k: Int,
	distance: Distance = EuclideanDistance,
	weighting: Double => Double = identity
) extends FittedModel[Nominal] {

	require(k <= schema.recordCount, "Cannot run kNN classifier on a dataset with less than k records.")

	def predict(data: Unlabeled) = ???
}
