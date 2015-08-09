package qlearn.wekas

import qlearn.loss.numerical.distance.Distance
import weka.core.DistanceFunction

case class WekaDistance(dist: Distance) extends DistanceFunction {

	// TODO: Implement all these crazy Weka methods

	// Members declared in weka.core.DistanceFunction
	def clean(): Unit = ???
	def distance(x$1: weka.core.Instance,x$2: weka.core.Instance,x$3: Double,x$4: weka.core.neighboursearch.PerformanceStats): Double = ???
	def distance(x$1: weka.core.Instance,x$2: weka.core.Instance,x$3: Double): Double = ???
	def distance(x$1: weka.core.Instance,x$2: weka.core.Instance,x$3: weka.core.neighboursearch.PerformanceStats): Double = ???
	def distance(x$1: weka.core.Instance,x$2: weka.core.Instance): Double = ???
	def getAttributeIndices(): String = ???
	def getInstances(): weka.core.Instances = ???
	def getInvertSelection(): Boolean = ???
	def postProcessDistances(x$1: Array[Double]): Unit = ???
	def setAttributeIndices(x$1: String): Unit = ???
	def setInstances(x$1: weka.core.Instances): Unit = ???
	def setInvertSelection(x$1: Boolean): Unit = ???
	def update(x$1: weka.core.Instance): Unit = ???

	// Members declared in weka.core.OptionHandler
	def getOptions(): Array[String] = ???
	def listOptions(): java.util.Enumeration[weka.core.Option] = ???
	def setOptions(x$1: Array[String]): Unit = ???
}
