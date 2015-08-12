package qlearn.wekas.cluster

import qlearn.ml.Randomized
import qlearn.wekas.WekaClusterer
import weka.clusterers.Cobweb

import scala.util.Random

case class CobWeb(acuity: Double = 1.0, cutoff: Double = 0.002, seed: Long = Random.nextLong) extends WekaClusterer(new Cobweb) with Randomized {

	val c = clusterer.asInstanceOf[Cobweb]

	c.setAcuity(acuity)
	c.setCutoff(cutoff)
	c.setSeed(seed.toInt)
}
