import _root_.weka.clusterers.SimpleKMeans
import qlearn.dataset._

import qlearn.Types._
import qlearn.loss.numerical.distance.EuclideanDistance
import qlearn.ml
import qlearn.ml.classify.weka.{LogisticRegression, REPTree}
import qlearn.ml.classify.{RandomTree, SimpleKNN, FittedSimpleKNN}
import qlearn.ml.cluster.{weka, KMeans}
import qlearn.ml.meta.Bagging
import qlearn.ml.regress.RidgeRegression
import qlearn.validation.{SameDatasetValidation, CrossValidation}
import qlearn.wekas.WekaClusterer

object Main extends App {
	val ul = Unlabeled(Mat.rand(8, 10).t, Vector('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h))
	val l1 = Nominal('wheel, ul, Vector.fill(4)("axxxxxx") ++ Vector.fill(3)("b") ++ Vector.fill(2)("c") ++ Vector.fill(1)("d"))
	val l2 = Nominal('tire, ul, Vector.fill(1)("axyza") ++ Vector.fill(2)("b") ++ Vector.fill(3)("c") ++ Vector.fill(4)("d"))
	val lb = Binary('sold, ul, Vector.fill(6)(true) ++ Vector.fill(4)(false))
	val ln = MultiLabeled(
		Numerical('num, ul, (1 to 10).map(_.toDouble), EuclideanDistance),
		Numerical('num2, ul, (11 to 20).map(_.toDouble))
	)

	l1.report
	FittedSimpleKNN(l1, k = 3).predict(ul).report


	val l3 = MultiLabeled(l1, l2, lb)
	l3.report

	//LogisticRegression().fit(l3).predict(ul).report
	//REPTree().fit(l3).predict(ul).report
	RandomTree(minParent = 5).fit(l1).predict(ul).report

	val mdls = Seq.fill(300)(RandomTree(minParent = 1))
	Bagging(mdls).fit(l1).predict(ul).report

	//RidgeRegression(ridge = 0.0).fit(ln).predict(ul).report
	//ml.regress.weka.RidgeRegression(ridge = 0.0).fit(ln).predict(ul).report

	println(CrossValidation[Nominal](l1).validate(LogisticRegression()))
	println(SameDatasetValidation[Nominal](l1).validate(SimpleKNN(5)))



	val ulc = Unlabeled(Mat.rand(8, 50000).t, Vector('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h))
	println("a")
	KMeans(k = 50).cluster(ulc)
	println("b")
	weka.KMeans(k = 50)
	println("c")
	new WekaClusterer({
		val c = new SimpleKMeans
		c.setNumClusters(50)
		c
	}).cluster(ulc)
	println("x")










	val rul = Unlabeled(Mat.rand(8, 9).t, Vector('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h))
	val nm = Numerical('num, rul, (1 to 9).map(_.toDouble))
	println(nm.wekaDataset)
	RidgeRegression().fit(nm).predict(rul).report
	ml.regress.weka.RidgeRegression().fit(nm).predict(rul).report
}