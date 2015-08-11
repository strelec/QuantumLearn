name := "QuantumLearn"

version := "0.1"

scalaVersion := "2.11.7"

libraryDependencies  ++= Seq(
	"org.scalanlp" %% "breeze" % "0.12-SNAPSHOT",

	// native libraries greatly improve performance, but increase jar sizes.
	"org.scalanlp" %% "breeze-natives" % "0.12-SNAPSHOT",

	// weka
	"nz.ac.waikato.cms.weka" % "weka-dev" % "3.7.12"
)

resolvers ++= Seq(
	"Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
	"Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)