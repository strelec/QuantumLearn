package qlearn.dataset

import weka.core.Instances

abstract class SingleLabeled[+T <: SingleLabeled[T]] extends Labeled[T] {
	val name: Symbol = schema.name

	override def toString = s"Single(${x.labelString}, ${name.name})"

	def wekaDataset: Instances
}
