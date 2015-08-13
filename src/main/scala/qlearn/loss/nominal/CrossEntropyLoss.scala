package qlearn.loss.nominal

import breeze.linalg.{min, max, sum}
import breeze.numerics.log
import qlearn.dataset.Nominal
import qlearn.loss.Loss

case class CrossEntropyLoss(margin: Double = 1e-15) extends Loss[Nominal] {

	val range = 0.0 -> Double.PositiveInfinity

	def apply(actual: Nominal, predicted: Nominal) = {
		val corrected = min(max(predicted.ymat, margin), 1 - margin)
		sum(log(corrected) :* actual.ymat) / -actual.recordCount
	}
}
