name := """meecarros"""

version := "1.0-SNAPSHOT"

lazy val GatlingTest = config("gatling") extend Test

//lazy val root = (project in file(".")).enablePlugins(PlayJava, GatlingPlugin).configs(GatlingTest)
  //.settings(inConfig(GatlingTest)(Defaults.testSettings): _*)
  //.settings(
   // scalaSource in GatlingTest := baseDirectory.value / "/gatling/simulation"
 // )

 
lazy val root = (project in file(".")).enablePlugins(PlayJava, GatlingPlugin).configs(GatlingTest)
  .settings(inConfig(GatlingTest)(Defaults.testSettings): _*)
  .settings(
    scalaSource in GatlingTest := baseDirectory.value / "/gatling/simulation",
	JsEngineKeys.npmNodeModules in Assets := Nil,
    JsEngineKeys.npmNodeModules in TestAssets := Nil
  )
 
 
 

scalaVersion := "2.11.11"

libraryDependencies += filters
libraryDependencies += javaJpa

libraryDependencies += "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final"
libraryDependencies += "io.dropwizard.metrics" % "metrics-core" % "3.2.1"
libraryDependencies += "com.palominolabs.http" % "url-builder" % "1.1.0"
libraryDependencies += "net.jodah" % "failsafe" % "1.0.3"

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % Test
libraryDependencies += "io.gatling" % "gatling-test-framework" % "2.2.2" % Test

// https://mvnrepository.com/artifact/org.mockito/mockito-all
libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19"

libraryDependencies += javaWs

libraryDependencies +="io.swagger" %% "swagger-play2" % "1.5.3"
libraryDependencies +="org.webjars" %% "webjars-play" % "2.5.0-4"
libraryDependencies +="org.webjars" % "swagger-ui" % "2.2.0"

// https://mvnrepository.com/artifact/com.google.code.gson/gson
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.1"


PlayKeys.externalizeResources := false

// Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile)

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           // Java project. Don't expect Scala IDE
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)  // Use .class files instead of generated .scala files for views and routes

testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
herokuAppName in Compile := "meecarros"
herokuConfigVars in Compile := Map(
  "HEROKU_API_KEY" -> "5ef56961-7ec5-41ae-84e3-937a2caa4e8d"
)