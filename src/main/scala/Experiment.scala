import breeze.linalg.{pinv, sum}
import qlearn.Types.Mat

object Experiment extends App {
	def memory {
		System.gc
		System.gc
		System.gc
		System.gc
		System.gc
		println(s"Mem ${Runtime.getRuntime.freeMemory}")
	}





	{
		val n = 10000
		val a = Mat.rand[Double](n, n)

		var start = System.nanoTime
		val b = a(0 to -2, 0 to -2 by 2)
		println((System.nanoTime - start) / 1000000000.0)
	}



	println("START")
	memory
	val a = Mat.rand[Double](1300, 1300)
	println(sum(a))
	memory



	{
		val start = System.nanoTime
		pinv(a)
		println((System.nanoTime - start) / 1000000000.0)
	}
	memory

	println("A")

	{
		var start = System.nanoTime
		val b = a(0 to -2, 0 to -2)
		println((System.nanoTime - start) / 1000000000.0)

		memory

		start = System.nanoTime
		pinv(b)
		println((System.nanoTime - start) / 1000000000.0)
	}
	memory

	println("B")

	{
		var start = System.nanoTime
		val b = a(0 to -2, 0 to -2 by 2)
		println((System.nanoTime - start) / 1000000000.0)

		memory

		start = System.nanoTime
		pinv(b)
		println((System.nanoTime - start) / 1000000000.0)
	}
	memory

	println("C")

	{
		var start = System.nanoTime
		val bx = a((0 until a.rows).filter( _ => math.random < 0.5), ::)
		println((System.nanoTime - start) / 1000000000.0)

		memory

		start = System.nanoTime
		val b = bx.toDenseMatrix
		println((System.nanoTime - start) / 1000000000.0)

		memory

		start = System.nanoTime
		pinv(b)
		println((System.nanoTime - start) / 1000000000.0)
	}
	memory
}
