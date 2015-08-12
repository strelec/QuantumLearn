package qlearn.wekas.cluster

import qlearn.loss.numerical.distance.{EuclideanDistance, Distance}
import qlearn.ml.Randomized
import qlearn.wekas.{WekaDistance, WekaClusterer}
import weka.clusterers.SimpleKMeans

import scala.util.Random

case class KMeans(k: Int, distance: Distance = EuclideanDistance, maxIterations: Int = 500, seed: Long = Random.nextLong) extends WekaClusterer(new SimpleKMeans) with Randomized {

	val c = clusterer.asInstanceOf[SimpleKMeans]

	c.setNumClusters(k)
	c.setDistanceFunction(convertDistance(distance))
	c.setMaxIterations(maxIterations)
	c.setSeed(seed.toInt)
}
