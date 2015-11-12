package qlearn.ml.classify

import breeze.linalg.sum
import qlearn.Types._
import qlearn.dataset.Nominal
import qlearn.ml.{RandomizedModel, Model}
import qlearn.util.Util

import scala.util.Random

abstract class BinaryTree
case class Leaf(predict: Vec) extends BinaryTree
case class Node(left: BinaryTree, right: BinaryTree, feature: Int, split: Double) extends BinaryTree

case class RandomTree(
	numFeatures: Option[Int] = None,
	maxDepth: Option[Int] = None,
	minParent: Int = 1
) extends Model[Nominal] with RandomizedModel[Nominal] {

	require(minParent >= 1)

	def recurse(data: Nominal, indices: Vector[Int], rand: Random, depth: Int = 0): BinaryTree = {

		/*
			Pick a random feature set.

			This is the random portion of this learner, appropriately
			named RandomTree.
		 */

		def randomFeaturePick = {
			val range = 0 until data.featureCount
			numFeatures match {
				case Some(limit) => Util.randomSubset(range, limit, rand)
				case _ => range
			}
		}

		/*
			Compute the entropy of a distribution.

			Unit is nats instead ob bits.
		 */

		def entropy(v: Vec) = {
			val total = sum(v)
			math.log(total) - sum(v.map( d =>
				if (d == 0) 0 else d * math.log(d)
			)) / total
		}

		/*
			What is the best split value for a given feature?

			@returns (best split point, entropy)
		 */

		def bestSplitPoint(feature: Int, distribution: Vec): (Double, Double) = {
			var bestScore = Double.PositiveInfinity
			var best = 0.0

			val sorted = indices.sortBy(data.xmat(_, feature))
			val l = data.ymat(0, ::).t * 0.0
			val r = distribution
			sorted.indices.init.foreach { i =>
				val row = data.ymat(sorted(i), ::).t
				l += row
				r -= row

				val score = entropy(l) + entropy(r)
				if (score < bestScore) {
					bestScore = score
					best = data.xmat(sorted(i), feature) + data.xmat(sorted(i+1), feature)
				}
			}
			best/2 -> bestScore
		}

		/*
			Did we reach the stopping condition yet?

			React accordingly.
		 */

		val distribution =
			sum( data.ymat(indices, ::).toDenseMatrix.c ).t

		val shouldStop =
			indices.size <= minParent  ||          // we have less than minParent remaining
			maxDepth.exists(_ > depth) ||          // the depth is already too big
			(distribution :> 0.0).activeSize == 1  // the node is already pure

		if (shouldStop) Leaf(distribution) else {
			val (feature, (split, _)) =
				randomFeaturePick.map( feature =>
					feature -> bestSplitPoint(feature, distribution)
				).minBy(_._2._2)

			val (left, right) = indices.partition(data.xmat(_, feature) <= split)
			Node(
				recurse(data, left, rand, depth + 1),
				recurse(data, right, rand, depth + 1),
				feature, split
			)
		}
	}

	def fit(data: Nominal, seed: Long) = FittedRandomTree(
		data, recurse(data, data.indices.toVector, new Random(seed))
	)
}