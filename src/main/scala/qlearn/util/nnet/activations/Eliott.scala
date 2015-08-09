package qlearn.util.nnet.activations

import breeze.numerics._
import qlearn.Types.Vec

// Elliott, D.L. "A better activation function for artificial neural networks", 1993
// http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.46.7204&rep=rep1&type=pdf
case class Eliott(s: Double = 1.0) extends ActivationFunction {
	val min = 0.0
	val max = 1.0

	def compute(x: Vec) = {
		val tmp = x :* s
		tmp :/ 2.0 :/ (abs(tmp) :+ 1.0) :+ 0.5
	}

	def gradient(x: Vec, v: Vec) = {
		val tmp = abs(x :* s) :+ 1.0
		(tmp :^ -2.0) :* (s/2)
	}
}