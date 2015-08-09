package qlearn.wekas

import qlearn.Types.Mat
import qlearn.dataset.{SingleLabeled, Unlabeled, MultiLabeled}
import qlearn.ml.FittedModel
import weka.classifiers.Classifier

class WekaFittedModel[T <: SingleLabeled[T]](val schema: T, predictor: Classifier) extends FittedModel[T] {

	def predict(data: Unlabeled) = {
		val instances = data.wekaDataset
		val mat = Mat((0 until instances.numInstances).map { i =>
			instances.instance(i).setDataset( schema.wekaDataset )
			predictor.distributionForInstance(instances.instance(i))
		}: _*)
		schema.updated(data, mat)
	}


	override def toString = s"WekaFitted${predictor.getClass.getSimpleName}()"

	def report {
		println(predictor)
	}
}
