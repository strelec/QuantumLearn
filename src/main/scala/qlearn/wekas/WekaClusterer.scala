package qlearn.wekas

import qlearn.dataset.{NominalBasic, Unlabeled}
import qlearn.Types.Vec
import qlearn.loss.numerical.distance._
import qlearn.ml.Clusterer
import weka.clusterers.{Clusterer => ClustererW, AbstractClusterer}

class WekaClusterer(val clusterer: ClustererW) extends Clusterer {

	/*
		If the weka has our distance implemented, then use theirs,
		native version. Otherwise, wrap it with WekaDistance wrapper.
	 */

	protected def convertDistance(dist: Distance) = dist match {
		case EuclideanDistance => new weka.core.EuclideanDistance
		case MahnattanDistance => new weka.core.ManhattanDistance
		case ChebyshevDistance => new weka.core.ChebyshevDistance
		case NormDistance(p) =>
			val tmp = new weka.core.MinkowskiDistance
			tmp.setOrder(p)
			tmp

		case _ => WekaDistance(dist)
	}

	/*
		The main clustering method.
	 */

	def cluster(data: Unlabeled) = {
		val copy = AbstractClusterer.makeCopy(clusterer)
		val instances = data.wekaDataset
		copy.buildClusterer(instances)

		val y = Vec.tabulate(data.recordCount)( i =>
			copy.clusterInstance( instances.instance(i) )
		)

		val k = copy.numberOfClusters
		val names = (0 until k).map(_.toString).toVector
		NominalBasic('cluster, data, y, names)
	}

	override def toString = s"Weka${clusterer.getClass.getSimpleName}()"
}
