package qlearn.dataset.loaders

import scala.io.Source.fromFile

import qlearn.dataset.{Labeled, Unlabeled}

abstract class Loader {

	case class ParseError(error: String) extends Exception(error)


	def unlabeled(data: Iterator[String]): Unlabeled

	def unlabeled(file: String): Unlabeled = unlabeled(fromFile(file).getLines)


	def labeled[T <: Labeled[T]](data: Iterator[String], attribute: String): T

	def labeled[T <: Labeled[T]](file: String, attribute: String): T = labeled(fromFile(file).getLines, attribute)
}
