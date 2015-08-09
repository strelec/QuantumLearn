package qlearn.ml.regress

import qlearn.Types.Mat
import qlearn.dataset.{MultiLabeled, SingleLabeled, Unlabeled}
import qlearn.ml.FittedModel

case class FittedRidgeRegressionMulti[T <: SingleLabeled[T]](schema: MultiLabeled[T], coef: Mat) extends FittedModel[MultiLabeled[T]] {

	def predict(data: Unlabeled) = schema.updated(data, data.xmat * coef)
}
