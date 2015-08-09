import qlearn.dataset._

import qlearn.Types._
import qlearn.ml.cluster.KMeans
import qlearn.ml.regress.RidgeRegression
import qlearn.validation.CrossValidation
import qlearn.wekas.classify.{LogisticRegression, REPTree}
import qlearn.wekas.regress

object Main extends App {
	val ul = Unlabeled(Mat.rand(8, 10).t, Vector('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h))
	val l1 = Nominal('wheel, ul, Vector.fill(4)("axxxxxx") ++ Vector.fill(3)("b") ++ Vector.fill(2)("c") ++ Vector.fill(1)("d"))
	val l2 = Nominal('tire, ul, Vector.fill(1)("axyza") ++ Vector.fill(2)("b") ++ Vector.fill(3)("c") ++ Vector.fill(4)("d"))
	val lb = Binary('sold, ul, Vector.fill(6)(true) ++ Vector.fill(4)(false))
	val ln = MultiLabeled(
		Numerical('num, ul, (1 to 10).map(_.toDouble)),
		Numerical('num2, ul, (11 to 20).map(_.toDouble))
	)


	val l3 = MultiLabeled(l1, l2, lb)
	l3.report

	LogisticRegression().fit(l3).predict(ul).report
	REPTree().fit(l3).predict(ul).report

	RidgeRegression(ridge = 0.0).fit(ln).predict(ul).report
	regress.RidgeRegression(ridge = 0.0).fit(ln).predict(ul).report

	println(CrossValidation[Nominal](l1).validate(LogisticRegression()))



	KMeans(k = 3, maxIterations = 100).cluster(ul).report










	val rul = Unlabeled(Mat.rand(8, 9).t, Vector('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h))
	val nm = Numerical('num, rul, (1 to 9).map(_.toDouble))
	println(nm.wekaDataset)
	RidgeRegression().fit(nm).predict(rul).report
	regress.RidgeRegression().fit(nm).predict(rul).report
}