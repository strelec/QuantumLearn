package qlearn.dataset

import qlearn.Types._
import qlearn.dataset.schema.NumericalColumn
import qlearn.loss.Loss
import qlearn.loss.numerical.MeanSquaredLoss
import qlearn.util.Util
import weka.core.{Attribute, Instances}

case class Numerical(x: Unlabeled, y: Vec, schema: NumericalColumn) extends SingleLabeled[Numerical] {
	lazy val ymat = y.toDenseMatrix.t

	val width = 1

	def updated(xnew: Unlabeled, ynew: Mat) = schema.populate(xnew, ynew)


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
	def apply(name: Symbol, x: Unlabeled, y: Seq[Double], loss: Loss[Numerical] = MeanSquaredLoss): Numerical =
		Numerical(x, Vec(y: _*), NumericalColumn(name, loss))
}