package qlearn.wekas

import qlearn.dataset.{NominalBasic, Unlabeled}
import qlearn.Types.Vec
import qlearn.ml.Clusterer
import weka.clusterers.{Clusterer => ClustererW, AbstractClusterer}

class WekaClusterer(val clusterer: ClustererW) extends Clusterer {

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
