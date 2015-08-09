package qlearn.validation

import qlearn.dataset.{SingleLabeled, Nominal, Labeled}
import qlearn.ml.Model

// TODO: is this labeled?
case class CrossValidation[T <: SingleLabeled[T]](data: T, folds: Int = 10, stratify: Boolean = true) extends Validation[T] {

	/*
		This is a special, very optimized k-fold cross validation engine.

		The memory usage does not grow linearly with the number of folds, but is a constant. The dataset
		is copied at most 2 times, even if you do 100-fold cross validation.

		Space complexity: at most 2 * N
		Time complexity: O(N)
	 */

	require(folds >= 3, "The number of folds has to be at least three.")
	require(folds <= data.recordCount, "The number of folds cannot exceed the number of records.")

	/*
		An array that assigns the continuous range to each fold. All folds are the same size,
		except if the dataset is not evenly divisible by the number of folds -- in that case,
		folds at the begining have one record more.
	 */

	val ranges: Vector[Range] = {
		val div = data.recordCount / folds
		val mod = data.recordCount % folds
		val a = (0 to mod).map(_ * (div+1))
		val b = (mod+1 to folds).map(_ * div + mod)
		(a ++ b).sliding(2).map {
			case Seq(first, last) => first until last
		}.toVector
	}

	val rangesTwice = ranges ++ ranges.map { range =>
		range.start + data.recordCount to range.last + data.recordCount
	}

	def multiRange(a: Int, b: Int) = rangesTwice(a).start to rangesTwice(b-1).last

	/*
		An inteligent algoritm that ensures the clases in each individual fold are as evenly distributed
		as possible.
	 */

	def doStratify(data: Nominal) = {
		val classOf = data.indices.groupBy(data.y(_))
		val (names, counts) = classOf.mapValues(_.size).toVector.sortBy(_._2).unzip

		val remaining = counts.toArray

		val rangeIndices = ranges.map { range =>
			var size = range.size
			var curn = data.recordCount - range.min

			remaining.indices.flatMap { i =>
				val chose = (1.0 * remaining(i) * size / curn).round.toInt
				size -= chose
				curn -= remaining(i)
				remaining(i) -= chose

				classOf(names(i)).dropRight( remaining(i) ).takeRight(chose)
			}
		}

		val indices = (rangeIndices ++ rangeIndices.dropRight(2)).flatten
		data.pick(indices)
	}

	/*
		Main methods.
	 */

	val dataset =
		(data, stratify) match {
			case (d: Nominal, true) => doStratify(d).asInstanceOf[T]
			case _ => data ++ data(multiRange(0, folds - 2))
		}

	val learning =
		Vector.tabulate(folds) { fold =>
			dataset(multiRange(fold, fold + folds - 1))
		}

	val testing = ranges.map(dataset.apply)

	def validate(model: Model[T]) =
		(0 until folds).map { fold =>
			val prediction = model.fit(learning(fold)).predict(testing(fold).x)
			loss(testing(fold), prediction)
		}.sum / folds
}