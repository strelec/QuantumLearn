package qlearn.ml.classify

import qlearn.dataset.Nominal
import qlearn.loss.numerical.distance.{EuclideanDistance, Distance}
import qlearn.ml.Model

case class SimpleKNN(
	k: Int,
	distance: Distance = EuclideanDistance,
	weighting: Double => Double = {_ => 1.0}
) extends Model[Nominal] {

	def fit(data: Nominal) =
		FittedSimpleKNN(data, k, distance, weighting)
}
