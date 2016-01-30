package qlearn.validation

import qlearn.dataset.{Labeled, Nominal}
import qlearn.ml.Model

case class PercentageSplit[T <: Labeled[T]](data: T, percentage: Double = 70, stratify: Boolean = true) extends Validation[T] {

	/*
		Space complexity: N if stratified, O(1) otherwise
	 */

	val splitPoint = (data.recordCount / 100.0 * percentage).round.toInt

	require(splitPoint > 0, "There has to be at least one record in the learning dataset.")
	require(splitPoint < data.recordCount, "There has to be at least one record in the validation dataset.")

	/*
		An inteligent algoritm that ensures the clases in each individual fold are as evenly distributed
		as possible.
	 */

	def doStratify(data: Nominal) = {
		val classOf = data.indices.groupBy(data.y(_))
		val (names, counts) = classOf.mapValues(_.size).toVector.sortBy(_._2).unzip

		var size = splitPoint
		var curn = data.recordCount

		val (a, b) = (counts, names).zipped.map { (count, name) =>
			val chose = (1.0 * count * size / curn).round.toInt
			size -= chose
			curn -= count

			classOf(name).splitAt(chose)
		}.unzip

		data.pick((a ++ b).flatten)
	}

	/*
		Main methods.
	 */

	val dataset =
		(data, stratify) match {
			case (d: Nominal, true) => doStratify(d).asInstanceOf[T]
			case _ => data
		}

	val learning = dataset(0 until splitPoint)

	val testing = dataset(splitPoint to -1)

	def validate(model: Model[T]) = {
		val prediction = model.fit(learning).predict(testing.x)
		loss(testing, prediction)
	}
}