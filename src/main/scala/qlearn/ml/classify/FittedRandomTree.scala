package qlearn.ml.classify

import breeze.linalg.sum
import qlearn.Types._
import qlearn.dataset.{Nominal, Unlabeled}
import qlearn.ml.FittedModel

case class FittedRandomTree(
	schema: Nominal,
	tree: BinaryTree
) extends FittedModel[Nominal] {

	def predict(data: Unlabeled) = {
		val newy = data.xmat.r.map { vec =>

			def recurse(tree: BinaryTree): Vec =
				tree match {
					case Leaf(prediction) => prediction / sum(prediction)
					case Node(left, right, feature, split) =>
						recurse(
							if (vec(feature) < split) left else right
						)
				}

			recurse(tree)
		}
		schema.updated(data, newy)
	}
}
