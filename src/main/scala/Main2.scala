import qlearn.dataset.loaders.ArffLoader
import qlearn.ml.cluster.KMeans

object Main2 extends App {
	val data = ArffLoader.unlabeled("datasets/arff/regression/autoPrice.arff")

	KMeans(k = 5).cluster(data).report
}
