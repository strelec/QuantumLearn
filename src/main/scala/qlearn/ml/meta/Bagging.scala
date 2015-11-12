package qlearn.ml.meta

import qlearn.dataset.SingleLabeled
import qlearn.ml.{Model, Randomized}
import qlearn.util.Util

import scala.util.Random

case class Bagging[T <: SingleLabeled[T]](
	learners: Seq[Model[T]],
	bagSizePercentage: Double = 100,
	seed: Long = Random.nextLong
) extends Model[T] with Randomized {

	def fit(data: T) = {
		val rand = getRandom
		val bagSize = (bagSizePercentage * data.recordCount / 100).round.toInt

		val fitted = learners.map { learner =>
			val bag = Util.randomWithReplacement(data.indices, bagSize, rand)
			learner.fit(data.pick(bag))
		}

		FittedBagging(data, fitted)
	}
}

object Bagging {
	def apply[T <: SingleLabeled[T]](learner: Model[T], iterations: Int): Bagging[T] =
		Bagging(Seq.fill(iterations)(learner))
}