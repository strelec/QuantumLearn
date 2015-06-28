QuantumLearn
===

I have used Weka for quite some time now. There were quite some things that bothered me. The dataset management was clumsy at best, but I could deal with that. What I couldn't get around was the general slowness of the library. This is not only my complaint, just make a basic google search. The reason for that? Weka doesn't use BLAS (and neither does any other Java machine learning library). Not only that, many times unnecessary data copies are made.

So, here is the manifesto of the QuantumLearn library:

* Where possible, the **BLAS & LAPACK methods** are used. This brings up to 10x speedup compared to Weka.
* Created with support for **multilabel datasets** from the start. This enables much faster learning for algorithms that support that (NN, Linear / Logistic regression), otherwise it just fails back to training a separate model for each label.
* Created around the idea of **immutable data structures**. This eases multithreading. Example: When you call *.fit* on *LinearRegression*, you get *FittedLinearRegression* back.
* Algorithms are reporting their **progress status** during the training process. This is really important, since the training is usually a really long-lasting activity. It's nice to know how much time you have left to wait.

Beware, this library is in really early alpha stage. Do not use in production.

Dealing with the lack of algorithms
---
Until the algorithm is implemented in effective Scala code, the algorithm from Weka is wrapped. For now, this causes a dataset copy (but it is done only once).

Dealing with datasets
===
Let's first create an unlabeled (unsupervised) dataset. We create a matrix and name the features.

```scala
val unlabeled = Unlabeled(DenseMatrix(
	(1.0,2.0,3.0),
	(3.0,4.0,5.0),
	(4.0,5.0,6.0),
	(5.0,6.0,7.0)
), Vector("x", "y", "z"))
```

This dataset might be used for clustering or to make predictions on. However, if we want to learn from it, we have to label (supervize) it. Along with the dataset, you can specify a custom cost function, as is shown below with specifying hinge loss for the binary dataset.

```scala
val isMale = Binary("isMale", unlabeled, Vector(true, false, true, true), loss = HingeLoss)
val age    = Numerical("age", unlabeled, Vector(20.3, 56.8, 10.3, 11.8))
val major  = Nominal("major", unlabeled, Vector("ML", "literature", "ML", "art"))
```

You can then group many of those single-labeled datasets into a multi-labeled one.

```scala
val labeled = MultiLabeled(isMale, age, major)
```

Finally, to check everything is fine so far, we call `labeled.report`:

```
       x         y         z          isMale       age  major=ML  major=literature  major=art
16.00000  2.000000  3.000000   ->   1.000000  20.30000  1.000000  0.00000000000000  0.0000000
3.000000  11.00000  5.500000   ->   0.000000  56.80000  0.000000  1.00000000000000  0.0000000
4.000000  8.000000  10.00000   ->   1.000000  10.30000  1.000000  0.00000000000000  0.0000000
5.000000  100.0000  7.000000   ->   1.000000  11.80000  0.000000  0.00000000000000  1.0000000
```

Transforming records, features and labels
===
Say we want to add two new features to the dataset. We can use arbitrary data or reuse existing. Here's how it's done:

```scala
val augmented = FeatureAdder(
	"isFemale"    -> (row => !row("isMale")),
	"ageInMonths" -> (row => row("age") * 12)
).transform(labeled)
```


Wrapping Weka learners
===

Everything Weka-connected resides in a `qlearn.algorithms.weka` package. Some of the Weka algorithms are already nicely wrapped. The ones that are not, you can wrap yourself:

```scala
// simple example
WekaWrapper(new J48)

// complex example
WekaWrapper({
	val tmp = new J48
	tmp.setMinNumObj(10)
	tmp.setUseLaplace(true)
	tmp
})
```

Once this is done, you can use Weka learners in the same manner as the native ones. It's that simple.

Future improvements
===
This section presents future ideas for optimization / improvement. We are not in a hurry, first just make sure everything works correctly.

Memory optimizations
---
* Think how could we adapt the learners to avoid copying the dataset K times on K-fold cross-validation. Ideas: bit masking vector, two phase learning (to splice out the test fold), ... There is no free luch, and this causes additional CPU costs and complicates the code and class design. Therefore debate whether this is reasonable - RAM is cheap novadays.
* Learners should drop the reference to the learning dataset as soon as they are done learning. For example, with linear regression, we just remember the coefficients and allow the original dataset to be garbage collected.
* Could sparse datasets easily be supported? What is the performance cost, since BLAS is not used? How could more advanced optimizations in Breeze library help? See also: [Breeze bug report](https://github.com/scalanlp/breeze/issues/360)

CPU & computational optimizations
---
* Which learners are capable of parallelism (or distributed computing - Hadoop, Akka, Spark)? This is not a huge concern, since things such as parameter selection (via cross validation), ensembles (bagging, stacking) are hugely parallel.
* Since loss functions usually have unchanging target predictions, would preprocessing help?

Algorithm improvements
---
* Do learning algorithms benefit from knowing the unsupervized future test data upfront? See: [CrossValidated question](https://stats.stackexchange.com/questions/156085/which-supervised-algorithms-benefit-from-knowing-future-inputs-upfront)

Misc
---
* Java interop. Make it possible to use this library in Java without any extended effort.