package qlearn.ml.cluster.weka

import qlearn.loss.numerical.distance.{Distance, EuclideanDistance}
import qlearn.ml.Randomized
import qlearn.wekas.WekaClusterer
import weka.clusterers.SimpleKMeans

import scala.util.Random

case class KMeans(k: Int, distance: Distance = EuclideanDistance, maxIterations: Int = 500, seed: Long = Random.nextLong) extends WekaClusterer(new SimpleKMeans) with Randomized {

	val c = clusterer.asInstanceOf[SimpleKMeans]

	c.setNumClusters(k)
	c.setDistanceFunction(convertDistance(distance))
	c.setMaxIterations(maxIterations)
	c.setSeed(seed.toInt)
}
