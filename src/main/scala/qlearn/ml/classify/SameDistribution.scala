package qlearn.ml.classify

import breeze.linalg.sum
import qlearn.Types._
import qlearn.dataset.Nominal
import qlearn.ml.Model

case class SameDistribution() extends Model[Nominal] {

	def fit(data: Nominal) = {
		val columnSums = sum(data.ymat.c).toDenseVector
		FittedSameDistribution(data, columnSums / sum(columnSums))
	}
}
