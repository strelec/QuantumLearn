package qlearn.ml.classify.weka

import qlearn.dataset.Nominal
import qlearn.ml.Randomized
import qlearn.wekas.WekaModel
import weka.classifiers.trees

import scala.util.Random

case class REPTree(
	maxDepth: Option[Int] = None,
	minInstancesPerLeaf: Int = 2,
	pruning: Boolean = true,
	seed: Long = Random.nextLong
) extends WekaModel[Nominal](new trees.REPTree) with Randomized {

	val m = model.asInstanceOf[trees.REPTree]

	m.setMaxDepth(maxDepth.getOrElse(-1))
	m.setMinNum(minInstancesPerLeaf)
	m.setNoPruning(!pruning)
	m.setSeed(seed.toInt)
}