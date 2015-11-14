package qlearn.dataset

import qlearn.Types._
import qlearn.dataset.schema.NominalColumn
import qlearn.loss.Loss
import qlearn.loss.nominal.CrossEntropyLoss
import weka.core.{Attribute, Instances}

abstract class Nominal extends SingleLabeled[Nominal] with Product with Serializable {
	val x: Unlabeled
	val ymat: Mat
	def values: Vector[String]

	def width = values.size

	val y: IntVec =
		Vec.tabulate(recordCount) { i =>
			val inner = ymat(i, ::).inner.toArray
			val max = inner.max
			inner.indexOf(max)
		}

	/*
		Produce the weka dataset (Instances).
	 */

	lazy val wekaDataset = {
		val data = new Instances(x.wekaDataset)
		val pos = data.numAttributes

		import collection.JavaConversions._
		data.insertAttributeAt(new Attribute("output", values), pos)
		data.setClassIndex(pos)

		indices.foreach { i =>
			data.instance(i).setValue(pos, values(y(i)))
		}

		data
	}
}

object Nominal {
	def apply(name: Symbol, x: Unlabeled, y: Seq[String], loss: Loss[Nominal] = CrossEntropyLoss()): NominalBasic = {
		val values = y.distinct.toVector
		val lookup = values.zipWithIndex.toMap

		NominalBasic(x, Vec.tabulate(y.size)(y andThen lookup), NominalColumn(name, values), loss)
	}
}