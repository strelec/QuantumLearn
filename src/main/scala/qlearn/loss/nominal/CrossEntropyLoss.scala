package qlearn.loss.nominal

import breeze.linalg.sum
import breeze.numerics.log
import qlearn.dataset.Nominal
import qlearn.loss.Loss

case class CrossEntropyLoss(margin: Double = 1e-10) extends Loss[Nominal] {
	// TODO: implement margin
	def apply(actual: Nominal, predicted: Nominal) =
		sum(log(predicted.ymat) :* actual.ymat) / -actual.recordCount
}
