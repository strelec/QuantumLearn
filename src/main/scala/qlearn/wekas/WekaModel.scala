package qlearn.wekas

import qlearn.dataset.{MultiLabeled, SingleLabeled}
import qlearn.ml.Model
import weka.classifiers.{AbstractClassifier, Classifier}

class WekaModel[T <: SingleLabeled[T]](val model: Classifier) extends Model[T] {

	def fit(data: T) =
		new WekaFittedModel(data, {
			val copy = AbstractClassifier.makeCopy(model)
			copy.buildClassifier(data.wekaDataset)
			copy
		})

	override def toString = s"Weka${model.getClass.getSimpleName}()"
}
