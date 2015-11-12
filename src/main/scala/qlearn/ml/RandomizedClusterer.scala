package qlearn.ml

import qlearn.dataset.{Unlabeled, NominalBasic}

import scala.util.Random

trait RandomizedClusterer extends Clusterer {
	/*
		A clusterer should include this trait if its output significantly
		depends on the behavior of the random number generator.
	 */

	def cluster(data: Unlabeled, seed: Long): NominalBasic

	def cluster(data: Unlabeled) = cluster(data, Random.nextLong)
}