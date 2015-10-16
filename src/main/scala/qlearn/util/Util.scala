package qlearn.util

import scala.util.Random

object Util {
	val superScript = Vector('⁰', '¹', '²', '³', '⁴', '⁵', '⁶', '⁷', '⁸', '⁹')

	def toSuperScript(n: Int) = n.toString.map(_.asDigit).map(superScript).mkString

	def printDoubleNicely(num: Double, places: Int = 8) = {
		val str = s"%.${places - 1}f" format num take (places + 1)
		if (str == "NaN") {
			"_" * places
		} else if (str.startsWith("0.000") && num != 0 || str.startsWith("-0.000") || !str.contains('.')) {
			val Array(mant, rawExp) = s"%.${places}e" format num split "e\\+?"
			val exp = rawExp.toInt

			val len = places - exp.toString.size - 1
			s"%.${len}se%s" format (mant, exp)
		} else str.init
	}


	def randomSubset[T](items: IndexedSeq[T], k: Int, rnd: Random = Random) = {
		val n = items.size
		require(n >= k, s"Not enough elements: Cannot select $k from $n")

		var res = List.empty[T]
		(n - k until n).foreach { i =>
			val pos = rnd.nextInt(i+1)
			val item = items(pos)
			res ::= (if (res contains item) items(i) else item)
		}
		res
	}

	def randomWithReplacement[T](items: IndexedSeq[T], k: Int, rnd: Random = Random) =
		Vector.fill(k)(
			items(rnd.nextInt(items.size))
		)
}
