package qlearn.wekas.classify

import qlearn.dataset.Nominal
import qlearn.wekas.WekaModel
import weka.classifiers.trees

import scala.util.Random

case class REPTree(
	maxDepth: Option[Int] = None,
	minInstancesPerLeaf: Int = 2,
  pruning: Boolean = true,
	override val random: Random.type = Random
) extends WekaModel[Nominal](new trees.REPTree) {

	val m = model.asInstanceOf[trees.REPTree]

	m.setSeed(random.nextInt)
	m.setMaxDepth(maxDepth.getOrElse(-1))
	m.setMinNum(minInstancesPerLeaf)
	m.setNoPruning(!pruning)
}