package qlearn.wekas.regress

import qlearn.dataset.Numerical
import qlearn.wekas.WekaModel
import weka.classifiers.functions.LinearRegression

case class RidgeRegression(
	ridge: Double = 0,
	eliminateColinear: Boolean = false,
  conserveMemory: Boolean = false
) extends WekaModel[Numerical](new LinearRegression) {

	val m = model.asInstanceOf[LinearRegression]

	m.setRidge(ridge)
	m.setEliminateColinearAttributes(eliminateColinear)
	m.setMinimal(conserveMemory)
}
