package qlearn.loss

abstract class Loss[-T] {
	def apply(actual: T, predicted: T): Double
}
