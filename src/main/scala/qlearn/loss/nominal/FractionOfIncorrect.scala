package qlearn.loss.nominal

import qlearn.dataset.Nominal
import qlearn.loss.Loss

object FractionOfIncorrect extends Loss[Nominal] {

	def apply(actual: Nominal, predicted: Nominal) =
		(actual.y :!= predicted.y).activeSize / actual.recordCount.toDouble
}
