package qlearn

import breeze.linalg.{*, DenseMatrix, Matrix, DenseVector}

object Types {
	type Vec = DenseVector[Double]
	type Mat = DenseMatrix[Double]

	val Vec = DenseVector
	val Mat = DenseMatrix

	type IntVec = DenseVector[Int]
	type BinVec = DenseVector[Boolean]

	implicit class MatWrapper(m: Mat) {
		def r = m(*, ::)

		def c = m(::, *)
	}
}
