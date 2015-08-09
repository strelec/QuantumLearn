package qlearn.util.nnet.activations

import breeze.numerics._
import qlearn.Types.Vec

object Tanh extends ActivationFunction {
	val min = -1.0
	val max = 1.0

	def compute(x: Vec) = tanh(x)

	def gradient(x: Vec, v: Vec) = -(v :^ 2.0) :+ 1.0
}