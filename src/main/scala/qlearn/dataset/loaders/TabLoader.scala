package qlearn.dataset.loaders

object TabLoader extends Loader {
	def unlabeled(data: Iterator[String]) = {
		val names = data.next.split("\t")
		val columns = names.size

		val types = data.next.split("\t", -1).map(_.trim).map {
			case "continuous" | "c" => 1
			case "discrete" | "d"   => 1
			case _ => throw new Exception("Only discrete and continous variables are supported.")
		}

		val flags = data.next.split("\t", -1).map(_.trim)

		require(columns == types.size, "Number of type columns doesn't match.")
		require(columns == flags.size, "Number of flag columns doesn't match.")

		var classNames = Vector.fill(columns)(Vector.empty[String])

		/*val rows = data.map { line =>
			(line.split("\t"), types, Iterator.from(0)).zipped.map {
				case ("?", _, _) => Double.NaN
				case (str, 1, _) => str.toDouble
				case (str, 2, i) =>
					val names = classNames(i)

					names.indexOf(str) match {
						case -1 =>
							classNames = classNames.updated(i, names :+ str)
							names.size.toDouble

						case j => j.toDouble
					}
			}
		}.toArray*/

		???
	}

	def labeled[Binary](data: Iterator[String], attribute: String) = ???
}
