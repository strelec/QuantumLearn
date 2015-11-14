package qlearn.dataset.schema

import qlearn.Types.Mat
import qlearn.dataset.{SingleLabeled, Unlabeled}

abstract class Column {
	type T// <: SingleLabeled[T]

	val name: Symbol

	def populate(x: Unlabeled, y: Mat): T
}