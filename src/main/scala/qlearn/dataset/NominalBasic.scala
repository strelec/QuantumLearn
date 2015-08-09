package qlearn.dataset

import breeze.linalg.argmax
import qlearn.Types._
import qlearn.loss.Loss
import qlearn.loss.nominal.CrossEntropyLoss
import qlearn.util
import qlearn.util.Util

case class NominalBasic(name: Symbol, x: Unlabeled, override val y: IntVec, names: Vector[String], loss: Loss[_ >: Nominal] = CrossEntropyLoss()) extends Nominal {

	lazy val ymat =
		Mat.tabulate(y.size, names.size) { (r, c) =>
			if (y(r) == c) 1.0 else 0.0
		}

	def updated(xnew: Unlabeled, ynew: Mat): NominalFull = {
		assert(ynew.cols == names.size)
		NominalFull(name, xnew, ynew, names, loss)
	}

	def updatedSame(xnew: Unlabeled, ynew: Mat): NominalBasic = {
		assert(ynew.cols == names.size)
		copy(x = xnew, y = argmax(ynew.r))
	}

	/*
		Function that writes the dataset to stdout
	 */

	lazy val reportHeader = {
		val len = names.map(_.size).max
		Seq(s"%${len}s" format name.name)
	}

	def reportLine(line: Int) =
		Seq(names(y(line)).padTo(reportHeader.head.size, ' '))
}