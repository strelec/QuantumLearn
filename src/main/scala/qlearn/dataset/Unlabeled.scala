package qlearn.dataset

import qlearn.Types.Mat
import qlearn.util.Util
import weka.core.{DenseInstance, Attribute, Instances}

case class Unlabeled(xmat: Mat, names: Vector[Symbol]) {
	val recordCount = xmat.rows
	val featureCount = xmat.cols

	require(recordCount > 0, "Dataset must have at least one record.")
	require(names.size == names.distinct.size, "The names have to be unique.")
	require(names.size == featureCount, "You have to name every feature.")



	def indices = 0 until recordCount

	def apply(range: Range): Unlabeled = copy(xmat = xmat(range, ::))
	def apply(index: Int): Unlabeled = apply(index to index)
	def pick(indices: Seq[Int]): Unlabeled = copy(xmat = xmat(indices, ::).toDenseMatrix)

	def ++(that: Unlabeled) = copy(xmat = Mat.vertcat(xmat, that.xmat))

	def duplicate = copy(xmat = xmat.copy)

	def labelString = s"${recordCount} records, ${featureCount} features"
	override def toString = s"Data($labelString)"

	/*
	Function that writes the dataset to stdout
	*/

	lazy val reportHeader =
		names.map("%9s" format _.name)

	def reportLine(line: Int) = {
		val vec = xmat(line, ::).inner.toScalaVector
		vec.zipWithIndex.map { case (v, i) =>
			Util.printDoubleNicely(v, reportHeader(i).size)
		}
	}

	def report {
		println(reportHeader mkString "  ")
		indices.foreach { i =>
			println(reportLine(i) mkString "  ")
		}
	}



	lazy val wekaDataset = {
		val attributes = new java.util.ArrayList[Attribute]()
		names.foreach { name =>
			attributes.add(new Attribute(name.name))
		}

		val data = new Instances("Dataset", attributes, 0)

		indices.foreach { i =>
			data.add(new DenseInstance(1.0, xmat(i, ::).inner.toArray))
		}

		data
	}
}











