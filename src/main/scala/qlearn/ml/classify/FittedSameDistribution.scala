package qlearn.ml.classify

import qlearn.Types.Vec
import qlearn.dataset.{Unlabeled, Nominal}
import qlearn.ml.FittedModel

case class FittedSameDistribution(schema: Nominal, prediction: Vec) extends FittedModel[Nominal] {

	def predict(data: Unlabeled) =
		schema.updated(data, Vec.ones[Double](data.recordCount) * prediction.t)
}
