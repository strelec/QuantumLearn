package qlearn.loss

abstract class Loss[-T] {

	/*
		The domain of the function.

		It always ranges from the better score to the worse.
	 */

	def range: (Double, Double)

	/*
		The principal method to be defined.
	 */

	def apply(actual: T, predicted: T): Double
}
