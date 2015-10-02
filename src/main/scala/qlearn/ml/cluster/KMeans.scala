package qlearn.ml.cluster

import breeze.linalg.argmin
import qlearn.dataset.{NominalBasic, Unlabeled}
import qlearn.Types._
import qlearn.loss.numerical.distance.{Distance, EuclideanDistance}
import qlearn.strategies.{NoRecentImprovement, Stopping}
import qlearn.util.Util
import qlearn.ml.{Randomized, Clusterer}

import scala.util.Random


case class KMeans(
	k: Int,
	distance: Distance = EuclideanDistance,
	strategy: Stopping = NoRecentImprovement(5),
	seed: Long = Random.nextLong
) extends Clusterer with Randomized {

	/*
		This is the algoritm that performs k-means clustering via the iterative approach.

	 */


	require(k > 1, "Clustering requires at least 2 target clusters.")

	def cluster(data: Unlabeled): NominalBasic = {
		require(k <= data.recordCount, "Cannot have more clusters than data points.")

		val mat = data.xmat
		var centroids = {
			val randomSubset = Util.randomSubset(data.indices, k, getRandom)
			mat(randomSubset, ::).toDenseMatrix
		}

		def closest(p: Vec): Int =
			argmin(distance(centroids, p))

		val y = strategy.apply {
			val updated = Mat.zeros[Double](k, data.featureCount)
			val counts = Vec.zeros[Double](k)

			mat.r.foreach { row =>
				val index = closest(row)
				updated(index, ::) += row.t
				counts(index) += 1.0
			}

			updated.c /= counts
			val error = distance.total(centroids, updated)
			centroids = updated

			error -> { () =>
				Vec.tabulate(data.recordCount)( i => closest(mat(i, ::).t) )
			}
		}

		NominalBasic('cluster, data, y, names(k))
	}
}