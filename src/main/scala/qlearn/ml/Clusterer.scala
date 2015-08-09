package qlearn.ml

import qlearn.dataset.{NominalBasic, Unlabeled}

abstract class Clusterer {

	def cluster(data: Unlabeled): NominalBasic
}
