package qlearn.dataset

import qlearn.Types.{BinVec, Mat, Vec}
import qlearn.loss.Loss
import qlearn.loss.binary.LogisticLoss
import qlearn.loss.nominal.CrossEntropyLoss
import qlearn.util.Util

case class Binary(name: Symbol, x: Unlabeled, yb: Vec, loss: Loss[Binary]) extends Nominal {

	val names = Vector("yes", "no")

	lazy val ymat =
		Mat(yb.map( v =>
			Seq(v, 1-v)
		).toScalaVector: _*)

	def updated(xnew: Unlabeled, ynew: Mat): Binary = {
		assert(ynew.cols == names.size)
		copy(x = xnew, yb = ynew(::, 0))
	}

	/*
		Function that writes the dataset to stdout
		*/

	val reportHeader = Seq("%9s" format name.name)

	def reportLine(line: Int) =
		Seq(Util.printDoubleNicely(yb(line), reportHeader.head.size))
}

object Binary {

	def apply(name: Symbol, x: Unlabeled, seq: Seq[Boolean], loss: Loss[Binary] = LogisticLoss): Binary = {
		Binary(name, x, Vec(seq.map { v =>
			if (v) 1.0 else 0.0
		}: _*), loss)
	}
}