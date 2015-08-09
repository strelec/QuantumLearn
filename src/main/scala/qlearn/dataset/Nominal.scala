package qlearn.dataset

import qlearn.Types._
import qlearn.loss.Loss
import qlearn.loss.nominal.CrossEntropyLoss
import weka.core.{Attribute, Instances}

abstract class Nominal extends SingleLabeled[Nominal] with Product with Serializable {
	val name: Symbol
	val x: Unlabeled
	val ymat: Mat
	val names: Vector[String]

	def width = names.size

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
		data.insertAttributeAt(new Attribute("output", names), pos)
		data.setClassIndex(pos)

		indices.foreach { i =>
			data.instance(i).setValue(pos, names(y(i)))
		}

		data
	}
}

object Nominal {
	def apply(name: Symbol, x: Unlabeled, y: Seq[String], loss: Loss[_ >: Nominal] = CrossEntropyLoss()): NominalBasic = {
		val names = y.distinct.toVector.sorted
		val lookup = names.zipWithIndex.toMap

		NominalBasic(name, x, Vec.tabulate(y.size)(y andThen lookup), names, loss)
	}
}