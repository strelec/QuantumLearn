package qlearn.ml

import qlearn.dataset.{Unlabeled, SingleLabeled, MultiLabeled}
import qlearn.Types.Mat

case class FittedModelMulti[T <: SingleLabeled[T]](schema: MultiLabeled[T], models: Seq[FittedModel[T]]) extends FittedModel[MultiLabeled[T]] {

	def predict(data: Unlabeled): MultiLabeled[T] = {
		val ynew = Mat.horzcat(models.map(_.predict(data).ymat): _*)
		schema.updated(data, ynew)
	}
}
