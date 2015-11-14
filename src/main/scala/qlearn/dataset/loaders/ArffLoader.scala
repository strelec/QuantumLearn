package qlearn.dataset.loaders

import qlearn.Types.{Vec, Mat}
import qlearn.dataset.schema.{Column, NominalColumn, NumericalColumn}
import qlearn.dataset.{Numerical, Unlabeled}
import qlearn.loss.numerical.MeanSquaredLoss

import scala.collection.mutable.ArrayBuffer

/*
	ARFF data file loader

	Missing features:
	* support for labeled datasets (figure out types)
	* support for string and date columns
	* support for sparse rows
	* support for instance weights (this will probably never be implemented)

 */

object ArffLoader extends Loader {

	def removeComment(line: String) =
		line.takeWhile(_ != '%')

	def trimLine(line: String) = line.trim

	def isEmptyLine(line: String) = line.isEmpty



	object Regex {
		// since the structure is really simple, there's no need to bother with parsers

		private val literal = "'?(.*?)'?"

		private val nominal = raw"\{\s*(.*?)\s*\}"

		private val kind = raw"(real|numeric|integer|string|date|relational|$nominal)"

		val name = raw"(?i)@relation\s+$literal\s*".r

		val attribute = raw"(?i)@attribute\s+$literal\s+$kind".r
	}



	def clean(data: Iterator[String]) =
		data.map(removeComment).map(trimLine).filterNot(isEmptyLine)

	def commaSplit(str: String) = str.split(raw"\s*,\s*").toVector

	def parseHeader(data: Iterator[String]) = {
		val name = data.next match {
			case Regex.name(name) => name
			case line => throw ParseError(s"The dataset has to start with @relation, got instead: $line")
		}

		val attributes = Stream.continually(data.next).takeWhile(_.toLowerCase != "@data").map {
			case Regex.attribute(name, "real" | "numeric" | "integer", _) => NumericalColumn(Symbol(name))

			case Regex.attribute(name, kind, null) =>
				throw ParseError(s"An attribute $name of type $kind is currently unsupported")

			case Regex.attribute(name, _, kind) => NominalColumn(Symbol(name), commaSplit(kind))

			case line =>
				throw ParseError(s"Proper attribute declaration expected, got instead: $line")
		}.toVector

		(name, attributes)
	}

	def parseLine(types: Vector[Column])(line: String) =
		(commaSplit(line), types).zipped.map {
			case ("?", _)   => Double.NaN
			case (value, col: NumericalColumn) => value.toDouble
			case (value, col: NominalColumn) =>
				col.lookup.get(value) match {
					case Some(pos) => pos.toDouble
					case _ => throw ParseError(s"Undeclared nominal value: $value")
				}
		}

	def buildUnlabeled(it: Iterator[Double], names: Vector[Symbol]) = {
		val array = it.toArray
		val cols = names.size
		val rows = array.size / cols
		val matrix = new Mat(rows, cols, array, 0, cols, true)
		Unlabeled(matrix, names)
	}



	def unlabeled(data: Iterator[String]) = {
		val cleaned = clean(data)
		val (name, columns) = parseHeader(cleaned)
		val names = columns.map(_.name)

		val it = cleaned.flatMap(parseLine(columns))
		buildUnlabeled(it, names)
	}

	def labeled(data: Iterator[String], attribute: Symbol) = {
		val cleaned = clean(data)
		val (name, columns) = parseHeader(cleaned)
		val names = columns.map(_.name)

		val index = names.indexOf(attribute)
		if (index == -1)
			throw ParseError(s"The attribute $attribute was not found in the dataset")

		val y = new ArrayBuffer[Double]
		val it = cleaned.flatMap { line =>
			val data = parseLine(columns)(line)
			y.append(data(index))
			data.patch(index, Nil, 1)
		}
		val x = buildUnlabeled(it, names.patch(index, Nil, 1))

		Numerical(x, new Vec(y.toArray), NumericalColumn(attribute), MeanSquaredLoss)
	}
}