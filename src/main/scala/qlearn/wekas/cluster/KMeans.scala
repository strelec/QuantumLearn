package qlearn.wekas.cluster

import qlearn.loss.numerical.distance.{EuclideanDistance, Distance}
import qlearn.wekas.{WekaDistance, WekaClusterer}
import weka.clusterers.SimpleKMeans

class KMeans(k: Int, distance: Distance = EuclideanDistance) extends WekaClusterer(new SimpleKMeans) {

	val c = clusterer.asInstanceOf[SimpleKMeans]

	c.setNumClusters(k)
	c.setDistanceFunction(convertDistance(distance))
}
