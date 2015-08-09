package qlearn.wekas.cluster

import qlearn.wekas.WekaClusterer
import weka.clusterers.Cobweb

import scala.util.Random

case class CobWeb(acuity: Double = 1.0, cutoff: Double = 0.002, random: Random = Random) extends WekaClusterer(new Cobweb) {

	val c = clusterer.asInstanceOf[Cobweb]

	c.setAcuity(acuity)
	c.setCutoff(cutoff)
	c.setSeed(random.nextInt)
}
