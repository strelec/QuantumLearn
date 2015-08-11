package qlearn.ml.classify

import breeze.linalg.sum
import qlearn.Types._
import qlearn.dataset.{Nominal, Unlabeled}
import qlearn.loss.numerical.distance.{EuclideanDistance, Distance}
import qlearn.ml.FittedModel

case class FittedSimpleKNN(
	schema: Nominal,
	k: Int,
	distance: Distance = EuclideanDistance,
	weighting: Double => Double = {_ => 1.0}
) extends FittedModel[Nominal] {

	/*
		This classifier predicts the class distribution probabilities according
		to the k nearest records and their actual distance.

		With the weighting function you can denote how you want to ponderize
		the records according to their distance. Some examples:

		{_ => 1.0}      : ponderize the points completely equally
		math.pow(_, -p) : exponential dampering for p > 0
		...
	 */

	require(k <= schema.recordCount, s"Cannot run $k-NN classifier on a dataset with just ${schema.recordCount} records.")

	/*
		This function returns the indices of k smallest
		elements of a vector.

		TODO: Sorting is slow, rewrite to MoM algorithm.
	 */

	private def kSmallest(distances: Vec, k: Int) = {
		val vec = distances.toScalaVector
		vec.indices.sortBy(vec).take(k)
	}

	def predict(data: Unlabeled) = {
		val newy = data.xmat.r.map { record =>
			val distances = distance(schema.xmat, record)
			val smallest = kSmallest(distances, k)

			// select k smallest distances and weight them
			val weighted = distances(smallest).map(weighting)
			// select k closest corresponding output rows
			val chosen = schema.ymat(smallest, ::).toDenseMatrix
			// weight those rows with "weighted" vector
			val result = sum((chosen.c :* weighted).c).toDenseVector
			// finally, normalize
			result / sum(result)
		}
		schema.updated(data, newy)
	}
}
