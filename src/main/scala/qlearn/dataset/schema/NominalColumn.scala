package qlearn.dataset.schema

case class NominalColumn(name: String, values: IndexedSeq[String]) extends Column {
	val lookup = values.zipWithIndex.toMap
}