package qlearn.dataset.loaders

import scala.io.Source.fromFile

import qlearn.dataset.{Numerical, Labeled, Unlabeled}

abstract class Loader {

	case class ParseError(error: String) extends Exception(error)


	def unlabeled(data: Iterator[String]): Unlabeled

	def unlabeled(file: String): Unlabeled = unlabeled(fromFile(file).getLines)


	def labeled(data: Iterator[String], attribute: String): Numerical

	def labeled(file: String, attribute: String): Numerical = labeled(fromFile(file).getLines, attribute)
}
