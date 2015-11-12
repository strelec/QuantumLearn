package qlearn.validation

import qlearn.dataset._
import qlearn.ml.Model

abstract class Validation[T <: Labeled[T]] {
	def data: T

	// TODO: ugliest hack in the world
	// T should have loss method, comment it out
	// see my stackoverflow questions
	def loss(a: T, b: T): Double = data match {
		case x: Numerical => x.loss(a.asInstanceOf[Numerical], b.asInstanceOf[Numerical])
		case x: Binary => x.loss(a.asInstanceOf[Binary], b.asInstanceOf[Binary])
		case x: NominalBasic => x.loss(a.asInstanceOf[Nominal], b.asInstanceOf[Nominal])
		//case x: NominalFull => x.loss(a.asInstanceOf[Nominal], b.asInstanceOf[Nominal])
	}

	/*def loss(a: MultiLabeled[T], b: MultiLabeled[T]): Double =
		(a.ys, b.ys).zipped.map(
			(as, bs) => loss(as, bs)
		).sum / a.ys.size*/

	def validate(model: Model[T]): Double
}
