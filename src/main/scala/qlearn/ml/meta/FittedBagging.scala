package qlearn.ml.meta

import breeze.linalg.sum
import qlearn.dataset.{SingleLabeled, Unlabeled}
import qlearn.ml.FittedModel

case class FittedBagging[T <: SingleLabeled[T]](
	schema: T,
	fitted: Seq[FittedModel[T]]
) extends FittedModel[T] {

	def predict(data: Unlabeled) = {
		val newy = sum(fitted.map(_.predict(data).ymat))
		schema.updated(data, newy / fitted.size.toDouble)
	}
}
