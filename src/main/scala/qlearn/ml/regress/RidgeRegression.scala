package qlearn.ml.regress

import breeze.linalg.{inv, pinv}
import qlearn.dataset.{Labeled, Numerical, MultiLabeled, SingleLabeled}
import qlearn.Types.Mat
import qlearn.ml.Model

case class RidgeRegression(ridge: Double = 0) extends Model[Numerical] {
	require(ridge >= 0, "The ridge parameter must be positive")

	private[this] def inverse[T <: Labeled[T]](data: T) =
		if (ridge != 0) {
			// less performant case
			val x = data.xmat
			val xt = x.t
			val diagonal = Mat.eye[Double](data.width) * ridge
			// TODO: Solve?
			inv(xt * x - diagonal) * xt
		} else pinv(data.xmat)

	def fit(data: Numerical) =
		FittedRidgeRegression(data, inverse(data) * data.ymat)

	override def fit(data: MultiLabeled[Numerical]) =
		FittedRidgeRegressionMulti(data, inverse(data) * data.ymat)
}
