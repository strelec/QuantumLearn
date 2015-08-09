package qlearn.ml.regress

import qlearn.Types.Mat
import qlearn.dataset.{MultiLabeled, SingleLabeled, Unlabeled}
import qlearn.ml.FittedModel

case class FittedRidgeRegression[T <: SingleLabeled[T]](schema: T, coef: Mat) extends FittedModel[T] {

	def predict(data: Unlabeled) = schema.updated(data, data.xmat * coef)
}
