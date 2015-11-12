package qlearn.ml.meta

import breeze.linalg.normalize
import qlearn.dataset.{Binary, Nominal, Unlabeled}
import qlearn.ml.FittedModel
import qlearn.Types._

case class FittedOneVsAll(
	schema: Nominal,
	fitted: Seq[FittedModel[Binary]]
) extends FittedModel[Nominal] {

	def predict(data: Unlabeled) = {
		val newy = Mat(fitted.map(_.predict(data).yb.toArray): _*).t
		schema.updated(data, normalize(newy.r, 1))
	}
}
