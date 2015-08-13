package qlearn.loss.numerical.distance

import breeze.linalg.sum
import qlearn.Types._
import qlearn.dataset.Numerical
import qlearn.loss.Loss

abstract class Distance extends Loss[Numerical] {

	def range = 0.0 -> Double.PositiveInfinity

	require(range._1 < range._2, "Smaller distance has to mean better.")

	
	def dot(a: Vec, b: Vec) = a.dot(b)
	def dot(a: Mat, b: Vec) = a.r.dot(b)
	def dot(a: Mat, b: Mat) = sum((a :* b).r)


	def apply(actual: Numerical, predicted: Numerical) =
		apply(actual.y, predicted.y)

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
		Compute a vector of distances between the coaligned
		rows of both matrices.
	 */

	def apply(a: Mat, b: Mat): Vec =
		Vec.tabulate(a.rows)( i =>
			apply(a(i, ::).t, b(i, ::).t)
		)

	/*
		For performance reasons, you can also override this method.
		It computes the sum of the distances that you obtain by
		taking the pairwise rows of the both matrices.
	 */

	def total(a: Mat, b: Mat): Double = sum(apply(a, b))
}
