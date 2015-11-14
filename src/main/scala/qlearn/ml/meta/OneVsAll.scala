package qlearn.ml.meta

import qlearn.dataset.schema.BinaryColumn
import qlearn.dataset.{Binary, Nominal}
import qlearn.loss.binary.LogisticLoss
import qlearn.ml.Model

case class OneVsAll(learner: Model[Binary]) extends Model[Nominal] {

	private def binarize(data: Nominal) =
		(0 to data.ymat.cols).map { klass =>
			val yb = data.y.map { v =>
				if (v == klass) 1.0 else 0.0
			}
			Binary(data.x, yb, BinaryColumn(data.name), LogisticLoss)
		}

	def fit(data: Nominal) =
		FittedOneVsAll(data, binarize(data).map(learner.fit))
}