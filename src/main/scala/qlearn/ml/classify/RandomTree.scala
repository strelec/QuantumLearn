package qlearn.ml.classify

import breeze.linalg.{max, min, sum}
import qlearn.Types._
import qlearn.dataset.Nominal
import qlearn.ml.{Randomized, Model}
import qlearn.util.Util

import scala.util.Random

abstract class BinaryTree
case class Leaf(predict: Vec) extends BinaryTree
case class Node(left: BinaryTree, right: BinaryTree, feature: Int, split: Double) extends BinaryTree

case class RandomTree(
	numAttributes: Option[Int] = None,
	maxDepth: Option[Int] = None,
	minParent: Int = 1,
	seed: Long = Random.nextLong
) extends Model[Nominal] with Randomized {

	require(minParent >= 1)

	def recurse(data: Nominal, indices: Vector[Int], rand: Random, depth: Int = 0): BinaryTree = {

		/*
			Pick a random feature set.

			This is the random portion of this learner, appropriately
			named RandomTree.
		 */

		def randomFeaturePick = {
			val range = 0 until data.featureCount
			numAttributes match {
				case Some(limit) => Util.randomSubset(range, limit, rand)
				case _ => range
			}
		}

		/*
			What is the best split value for a given feature?

			@returns (best split point, entropy)
		 */

		def bestSplitPoint(feature: Int, distribution: Vec): (Double, Double) = {
			var bestScore = Double.NegativeInfinity
			var best = 0.0

			val sorted = indices.sortBy(data.xmat(_, feature))
			val l = data.ymat(sorted.head, ::).t.copy
			val r = distribution - l
			sorted.indices.tail.init.foreach { i =>
				l += data.ymat(sorted(i), ::).t
				r -= data.ymat(sorted(i), ::).t

				val sumL = sum(l)
				val sumR = sum(r)
				val score =
					sum(l.map( d =>
						if (d == 0) 0 else d * math.log(d / sumL)
					)) + sum(r.map( d =>
						if (d == 0) 0 else d * math.log(d / sumR)
					))

				if (score > bestScore) {
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
				).maxBy(_._2._2)

			val (left, right) = indices.partition(data.xmat(_, feature) <= split)
			Node(
				recurse(data, left, rand, depth + 1),
				recurse(data, right, rand, depth + 1),
				feature, split
			)
		}
	}

	def fit(data: Nominal) = FittedRandomTree(
		data, recurse(data, data.indices.toVector, getRandom)
	)
}