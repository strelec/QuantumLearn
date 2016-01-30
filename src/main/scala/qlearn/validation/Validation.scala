package qlearn.validation

import qlearn.dataset._
import qlearn.ml.Model

abstract class Validation[T <: Labeled[T]] {
	def data: T

	// TODO: ugliest hack in the world
	// T should have loss method, comment it out
	// see my stackoverflow questions
	def loss(a: T, b: T): Double = data match {
		case x: Numerical => x.schema.loss(a.asInstanceOf[Numerical], b.asInstanceOf[Numerical])
		case x: Binary => x.schema.loss(a.asInstanceOf[Binary], b.asInstanceOf[Binary])
		case x: NominalBasic => x.schema.loss(a.asInstanceOf[Nominal], b.asInstanceOf[Nominal])
		case x: NominalFull => x.schema.loss(a.asInstanceOf[Nominal], b.asInstanceOf[Nominal])
	}

	def validate(model: Model[T]): Double
}
