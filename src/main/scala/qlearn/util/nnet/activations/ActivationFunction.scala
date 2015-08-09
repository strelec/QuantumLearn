package qlearn.util.nnet.activations

import qlearn.Types.Vec

abstract class ActivationFunction {
	val min: Double
	val max: Double

	def compute(x: Vec): Vec
}
