package qlearn.strategies

case class NoRecentImprovement(n: Int, maxIterations: Int = 500) extends Stopping {
	def apply[T](a: => (Double, () => T)) = {
		val call = a

		var best = call._2()
		var bestError = call._1
		var bestI = 1

		var i = 1
		while(i < maxIterations && i - bestI < n) {
			i += 1

			val (error, thunk) = a
			println(s"Error: $error")
			if (error < bestError) {
				best = thunk()
				bestError = error
				bestI = i
			} else {
				println("* result NOT improved")
			}
		}

		best
	}
}
