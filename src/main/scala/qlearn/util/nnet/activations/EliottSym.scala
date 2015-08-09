package qlearn.util.nnet.activations

import breeze.numerics._
import qlearn.Types.Vec

case class EliottSym(s: Double = 1.0) extends ActivationFunction {
	val min = -1.0
	val max = 1.0

	def compute(x: Vec) = {
		val tmp = x :* s
		tmp :/ (abs(tmp) + 1.0)
	}

	def gradient(x: Vec, v: Vec) = {
		val tmp = abs(x :* s) :+ 1.0
		(tmp :^ -2.0) :* s
	}
}