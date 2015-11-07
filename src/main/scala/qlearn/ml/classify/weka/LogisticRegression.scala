package qlearn.ml.classify.weka

import qlearn.dataset.Nominal
import qlearn.wekas.WekaModel
import weka.classifiers.functions.Logistic

case class LogisticRegression(
	ridge: Double = 1e-8,
  maxIterations: Option[Int] = None,
  useConjugateGradientDescent: Boolean = false
) extends WekaModel[Nominal](new Logistic) {

	val m = model.asInstanceOf[Logistic]

	m.setRidge(ridge)
	m.setMaxIts(maxIterations.getOrElse(-1))
	m.setUseConjugateGradientDescent(useConjugateGradientDescent)
}
