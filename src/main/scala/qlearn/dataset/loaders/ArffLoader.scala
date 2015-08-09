package qlearn.dataset.loaders

import breeze.linalg.DenseMatrix
import qlearn.Types.Mat
import qlearn.dataset.Unlabeled

object ArffLoader extends Loader {
	/*
		A few filters
	 */

	def removeComment(line: String) =
		line.takeWhile(_ != '%')

	def trimLine(line: String) = line.trim

	def notEmptyLine(line: String) = line.nonEmpty

	/*
		Regexes
	 */

	object Regex {
		private[this] val literal = "'?(.*?)'?"

		private[this] val nominal = "\\{.*\\}"

		private[this] val kind = s"(real|numeric|$nominal)"

		val name = s"(?i)@relation\\s+$literal\\s*".r

		val attribute = s"(?i)@attribute\\s+$literal\\s+$kind".r
	}

	/*
		Unlabeled dataset loader
	 */

	// TODO: handle space and unknown values (?) and nominal attributes
	def parseLine(line: String) =
		line.split(",").map(_.toDouble)

	def unlabeled(data: Iterator[String]) = {
		val prepared = data.map(removeComment).map(trimLine).filter(notEmptyLine)

		val name = prepared.next match {
			case Regex.name(name) => name
			case line => throw new Exception(s"The dataset has to start with @relation, got instead: $line")
		}

		val attributes = Stream.continually(prepared.next).takeWhile(_ != "@data").map {
			case Regex.attribute(name, kind) => (name, kind)
			case line => throw new Exception(s"Proper attribute expected, got instead: $line")
		}.toVector


		val matrix = {
			val array = prepared.flatMap(parseLine).toArray
			val cols = attributes.size
			val rows = array.size / cols
			new Mat(rows, cols, array, 0, cols, true)
		}
		val names = attributes.map(x => Symbol(x._1))

		Unlabeled(matrix, names)
	}

	def labeled[Numerical](data: Iterator[String], attribute: String) = {
		val prepared = data.map(removeComment).map(trimLine).filter(notEmptyLine)

		val name = prepared.next match {
			case Regex.name(name) => name
			case line => throw new Exception(s"The dataset has to start with @relation, got instead: $line")
		}

		val attributes = Stream.continually(prepared.next).takeWhile(_ != "@data").map {
			case Regex.attribute(name, kind) => (name, kind)
			case line => throw new Exception(s"Proper attribute expected, got instead: $line")
		}.toVector

		// TODO: deduplicate code, extend

		???
	}
}
