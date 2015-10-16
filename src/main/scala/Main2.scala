import qlearn.dataset.loaders.ArffLoader
import qlearn.ml.cluster.KMeans

object Main2 extends App {
	val data = ArffLoader.unlabeled("datasets/arff/regression/autoPrice.arff")
	//val data = ArffLoader.unlabeled("datasets/arff/classification9/anneal.ORIG.arff")
	data.report

	KMeans(k = 30).cluster(data).report
}
