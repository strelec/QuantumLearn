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
Until the algorithm is implemented in effective Scala code, the algorithm from Weka is wrapped.

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

This dataset might be used for clustering or to make predictions on. However, if we want to learn from it, we have to label (supervize) it.

```scala
val labeled = Labeled(unlabeled,
	"isMale" -> Binary(true, false, true, true),
	"age"    -> Numerical(20.3, 56.8, 30.3, 34.8),
	"study"  -> Nominal("ML", "literature", "ML", "art")
)
```

Transforming records, features and labels
===
Say we want to add two new features to the dataset. We can use arbitrary data or reuse existing.

```scala
val augmented = FeatureAdder(
	"isFemale"    -> (row => !row("isMale")),
	"ageInMonths" -> (row => row("age") * 12)
).transform(labeled)
```

The variable `augmented` now contains this dataset:

```
todo
```

In machine learning, it is often useful to transform the feature space to include all linear combinations of features, up to some power.

```scala
val combined = LinearCombiner(
	upToPower = 2
	addBias = true
).transform(labeled)
```


...
