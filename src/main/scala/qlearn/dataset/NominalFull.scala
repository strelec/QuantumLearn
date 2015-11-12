package qlearn.dataset

import qlearn.Types._
import qlearn.loss.Loss
import qlearn.loss.nominal.CrossEntropyLoss
import qlearn.util.Util

case class NominalFull(name: Symbol, x: Unlabeled, ymat: Mat, names: Vector[String], loss: Loss[Nominal] = CrossEntropyLoss()) extends Nominal {

	def updated(xnew: Unlabeled, ynew: Mat): NominalFull = {
		assert(ynew.cols == names.size)
		copy(x = xnew, ymat = ynew)
	}


	/*
		Function that writes the dataset to stdout
		*/

	lazy val reportHeader =
		names.map(x =>
			"%9s" format s"${name.name}=$x"
		)

	def reportLine(line: Int) = {
		val vec = ymat(line, ::).inner.toScalaVector
		vec.zipWithIndex.map { case (v, i) =>
			Util.printDoubleNicely(v, reportHeader(i).size)
		}
	}
}
