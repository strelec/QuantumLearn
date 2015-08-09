package qlearn.util.nnet.activations

import breeze.numerics._
import qlearn.Types.Vec

object Sigmoid extends ActivationFunction {
	val min = 0.0
	val max = 1.0

	def compute(x: Vec) = (exp(-x) :+ 1.0) :^ -1.0

	def gradient(x: Vec, v: Vec) = v :* (-v :+ 1.0)
}