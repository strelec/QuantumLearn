package qlearn.dataset.schema

case class NominalSchema(name: String, values: IndexedSeq[String]) extends Schema {
	val lookup = values.zipWithIndex.toMap
}