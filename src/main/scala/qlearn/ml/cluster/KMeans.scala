package qlearn.ml.cluster

import breeze.linalg.argmin
import qlearn.dataset.{NominalBasic, Unlabeled}
import qlearn.Types._
import qlearn.loss.numerical.distance.{Distance, EuclideanDistance}
import qlearn.util.Util
import qlearn.ml.Clusterer

import scala.util.Random


case class KMeans(k: Int, distance: Distance = EuclideanDistance, eps: Double = 10e-6, maxIterations: Int = 500, random: Random = Random) extends Clusterer {

	/*
		This is the algoritm that performs k-means clustering via the iterative approach.

		TODO:
			* Store current best result to be returned
	 */


	require(k > 1, "Clustering requires at least 2 target clusters.")

	def cluster(data: Unlabeled): NominalBasic = {
		require(k <= data.recordCount, "Cannot have more clusters than data points.")

		val mat = data.xmat
		var centroids = {
			val randomSubset = Util.randomSubset(data.indices, k, random)
			mat(randomSubset, ::).toDenseMatrix
		}

		def closest(p: Vec): Int =
			argmin(distance(centroids, p))

		Iterator.fill(maxIterations) {
			val updated = Mat.zeros[Double](k, data.featureCount)
			val counts = Vec.zeros[Double](k)

			// TODO: foreach
			mat.r.map { row =>
				val index = closest(row)
				updated(index, ::) += row.t
				counts(index) += 1.0
				0.0
			}

			updated.c /= counts
			val error = distance(centroids, updated)
			centroids = updated

			println(s"Error: $error")
			error
		}.sliding(4).takeWhile {
			case Seq(prev, _, _, cur) => prev - cur > 4*eps
		}.toVector

		val y = Vec.tabulate(data.recordCount)( i => closest(mat(i, ::).t) )
		NominalBasic('cluster, data, y, names(k))
	}
}