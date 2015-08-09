package qlearn.util.nnet

import qlearn.util.nnet.activations.ActivationFunction

case class Layer(size: Int, activation: ActivationFunction) {
	require(size > 0, "Layer must have at least one neuron.")
}
