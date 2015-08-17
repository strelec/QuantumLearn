package qlearn.ml.meta

import qlearn.dataset.Nominal
import qlearn.ml.{Model, Randomized}
import qlearn.util.Util

import scala.util.Random

case class Bagging(
	learners: Seq[Model[Nominal]],
	bagSizePercentage: Double = 100,
	seed: Long = Random.nextLong
) extends Model[Nominal] with Randomized {

	def fit(data: Nominal) = {
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
	def apply(learner: Model[Nominal], iterations: Int): Bagging =
		Bagging(Seq.fill(iterations)(learner))
}