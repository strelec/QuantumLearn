package qlearn.dataset

import qlearn.Types._
import qlearn.loss.Loss
import qlearn.loss.numerical.MeanSquaredLoss
import qlearn.util.Util
import weka.core.{Attribute, Instances}

case class Numerical(name: Symbol, x: Unlabeled, y: Vec, loss: Loss[_ >: Numerical]) extends SingleLabeled[Numerical] {
	lazy val ymat = y.toDenseMatrix.t

	val width = 1

	def updated(xnew: Unlabeled, ynew: Mat) = {
		assert(ynew.cols == 1)
		copy(x = xnew, y = ynew(::, 0))
	}


	/*
		Function that writes the dataset to stdout
	 */

	val reportHeader = Seq("%9s" format name.name)

	def reportLine(line: Int) =
		Seq(Util.printDoubleNicely(y(line), reportHeader.head.size))


	lazy val wekaDataset = {
		val data = new Instances(x.wekaDataset)
		val pos = data.numAttributes
		data.insertAttributeAt(new Attribute("output"), pos)
		data.setClassIndex(pos)

		(0 until y.length).foreach { i =>
			data.instance(i).setValue(pos, y(i))
		}

		data
	}
}

object Numerical {
	def apply(name: Symbol, x: Unlabeled, y: Seq[Double], loss: Loss[_ >: Numerical] = MeanSquaredLoss): Numerical =
		Numerical(name, x, Vec(y: _*), loss)
}