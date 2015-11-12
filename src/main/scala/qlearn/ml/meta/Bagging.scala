package qlearn.ml.meta

import qlearn.dataset.SingleLabeled
import qlearn.ml.{Model, RandomizedModel}
import qlearn.util.Util

import scala.util.Random

case class Bagging[T <: SingleLabeled[T]](
	learners: Seq[Model[T]],
	bagSizePercentage: Double = 100
) extends Model[T] with RandomizedModel[T] {

	def fit(data: T, seed: Long) = {
		val rand = new Random(seed)
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