name := "QuantumLearn"

version := "0.1"

scalaVersion := "2.11.7"

libraryDependencies  ++= Seq(
	"org.scalanlp" %% "breeze" % "0.11.2",

	// native libraries greatly improve performance, but increase jar sizes.
	"org.scalanlp" %% "breeze-natives" % "0.11.2",

	// weka
	"nz.ac.waikato.cms.weka" % "weka-dev" % "3.7.12"
)
