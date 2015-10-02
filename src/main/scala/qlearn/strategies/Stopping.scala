package qlearn.strategies

abstract class Stopping {
	def apply[T](a: => (Double, () => T)): T
}
