package qlearn.ml.meta

import breeze.linalg.sum
import qlearn.dataset.{Nominal, Unlabeled}
import qlearn.ml.FittedModel

case class FittedBagging(
	schema: Nominal,
	fitted: Seq[FittedModel[Nominal]]
) extends FittedModel[Nominal] {

	def predict(data: Unlabeled) = {
		val newy = sum(fitted.map(_.predict(data).ymat))
		schema.updated(data, newy / fitted.size.toDouble)
	}
}
