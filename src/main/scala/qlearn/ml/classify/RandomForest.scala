package qlearn.ml.classify

import qlearn.dataset.Nominal
import qlearn.ml.{RandomizedModel, Model}
import qlearn.ml.meta.Bagging

case class RandomForest(tree: RandomTree, count: Int) extends Model[Nominal] with RandomizedModel[Nominal] {

	def fit(data: Nominal, seed: Long) =
		Bagging(Seq.fill(count)(tree)).fit(data, seed)
}
