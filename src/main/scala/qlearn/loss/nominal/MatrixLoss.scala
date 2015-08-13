package qlearn.loss.nominal

import breeze.linalg.sum
import qlearn.dataset.Nominal
import qlearn.loss.Loss

import qlearn.Types.Mat

case class MatrixLoss(matrix: (Symbol, Seq[Double])*) extends Loss[Nominal] {

	val range = 0.0 -> Double.PositiveInfinity

	val m = Mat(matrix.sortBy(_._1.name).map(_._2): _*)

	def apply(actual: Nominal, predicted: Nominal) =
		sum(actual.ymat * m :* predicted.ymat) / actual.recordCount
}
