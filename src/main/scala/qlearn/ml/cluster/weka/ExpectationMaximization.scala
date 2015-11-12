package qlearn.ml.cluster.weka

import qlearn.ml.RandomizedClusterer
import qlearn.wekas.WekaClusterer
import weka.clusterers.EM

import scala.util.Random

case class ExpectationMaximization(
	k: Option[Int] = None,
	folds: Int = 10,
	runs: Int = 10,
	maxClusters: Option[Int] = None,
	maxIterations: Int = 100,
	epsForK: Double = 1e-6,
	epsForE: Double = 1e-6,
	seed: Long = Random.nextLong
) extends WekaClusterer(new EM) {

	val c = clusterer.asInstanceOf[EM]

	c.setNumClusters(k.getOrElse(-1))
	c.setNumFolds(folds)
	c.setNumKMeansRuns(runs)
	c.setMaximumNumberOfClusters(maxClusters.getOrElse(-1))
	c.setMaxIterations(maxIterations)
	c.setMinLogLikelihoodImprovementCV(epsForK)
	c.setMinLogLikelihoodImprovementIterating(epsForE)
	c.setSeed(seed.toInt)
}
