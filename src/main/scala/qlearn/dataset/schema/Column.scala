package qlearn.dataset.schema

import qlearn.Types.Mat
import qlearn.dataset.{SingleLabeled, Unlabeled}
import qlearn.loss.Loss

abstract class Column {
	type T// <: SingleLabeled[T]

	val loss: Loss[T]

	val name: Symbol

	def populate(x: Unlabeled, y: Mat): T
}