package qlearn.ml

import qlearn.dataset.{NominalBasic, Unlabeled}

abstract class Clusterer {

	protected def names(k: Int) = (0 until k).map(_.toString).toVector

	def cluster(data: Unlabeled): NominalBasic
}
