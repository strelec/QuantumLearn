package qlearn.loss.numerical.distance

import qlearn.Types._

abstract class Distance {

	/*
		Compute the distance between two vectors.
	 */

	def apply(a: Vec, b: Vec): Double

	/*
		Compute a vector of distances to all the rows
		of the matrix a.
	 */

	def apply(a: Mat, b: Vec): Vec =
		a.r.map( row =>
			apply(row, b)
		)

	/*
		For performance reasons, you can also override this method.
		It computes the sum of the distances that you obtain by
		taking the pairwise rows of the both matrices.
	 */

	def apply(a: Mat, b: Mat): Double =
		(0 until a.rows).map( i =>
			apply(a(i, ::).t, b(i, ::).t)
		).sum
}
