package qlearn.dataset

import qlearn.Types.Mat
import qlearn.loss.Loss

import scala.util.Random

abstract class Labeled[+T <: Labeled[T]] {
	def x: Unlabeled
	def xmat = x.xmat

	def recordCount = x.recordCount
	def featureCount = x.featureCount

	def ymat: Mat

	// TODO
	//require(ymat.rows == xmat.rows, "Both X and Y have to have the same number of rows")

	// TODO: this should be here to avoid the ugly hack
	//type TT <: T
	//type Q >: TT

	//def loss: Loss[Q]


	//def loss[Q >: T]: Loss[Q]
	//def loss: Loss[_ >: T]

	def width: Int

	def indices = 0 until recordCount

	def apply(range: Range): T = updated(x(range), ymat(range, ::))
	def apply(index: Int): T = apply(index to index)
	def pick(indices: Seq[Int]): T = updated(x.pick(indices), ymat(indices, ::).toDenseMatrix)

	def updated(xnew: Unlabeled, ynew: Mat): T

	def ++[Q <: Labeled[Q]](that: Q): T = updated(x ++ that.x, Mat.vertcat(ymat, that.ymat))

	def duplicate: T = updated(x.duplicate, ymat.copy)

	def shuffle: T =
		pick( Random.shuffle(0 to recordCount - 1) )

	/*
		Function that writes the dataset to stdout
	 */

	def reportHeader: Seq[String]
	def reportLine(line: Int): Seq[String]

	def report {
		println(x.reportHeader ++ Vector("    ") ++ reportHeader mkString "  ")

		indices.foreach { i =>
			println(x.reportLine(i) ++ Vector(" -> ") ++ reportLine(i) mkString "  ")
		}
	}
}
