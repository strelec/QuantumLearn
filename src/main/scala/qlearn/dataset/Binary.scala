package qlearn.dataset

import qlearn.Types.{BinVec, Mat, Vec}
import qlearn.dataset.schema.BinaryColumn
import qlearn.loss.Loss
import qlearn.loss.binary.LogisticLoss
import qlearn.loss.nominal.CrossEntropyLoss
import qlearn.util.Util

case class Binary(x: Unlabeled, yb: Vec, schema: BinaryColumn, loss: Loss[Binary]) extends Nominal {

	val values = Vector("no", "yes")

	lazy val ymat =
		Mat(yb.map( v =>
			Seq(1-v, v)
		).toScalaVector: _*)

	val yt = yb :>= 0.5

	override val y =
		yb.map( v =>
			if (v >= 0.5) 1 else 0
		)

	def updated(xnew: Unlabeled, ynew: Mat): Binary = schema.populate(xnew, ynew)

	/*
		Function that writes the dataset to stdout
		*/

	val reportHeader = Seq("%9s" format name.name)

	def reportLine(line: Int) =
		Seq(Util.printDoubleNicely(yb(line), reportHeader.head.size))
}

object Binary {

	def apply(name: Symbol, x: Unlabeled, seq: Seq[Boolean], loss: Loss[Binary] = LogisticLoss): Binary = {
		Binary(x, Vec(seq.map { v =>
			if (v) 1.0 else 0.0
		}: _*), BinaryColumn(name), loss)
	}
}