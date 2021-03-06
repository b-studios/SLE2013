//A sample SBT file for using macros (and macro-paradise).
//
//To use macros, we need to have separate projects.
val macros = project in file("macros")
val root = project in file(".") dependsOn macros

//We need to specify the next build settings for all projects. Hence, we use ThisBuild.
scalacOptions in ThisBuild ++= Seq("-language:experimental.macros", "-feature", "-deprecation", "-unchecked", "-language:implicits")

scalaVersion in ThisBuild := "2.10.3"

// Add Scala reflection library. This is is needed for all projects using reflection, macros or both.
libraryDependencies in ThisBuild += "org.scala-lang" % "scala-reflect" % scalaVersion.value

//Add macro-paradise support library.
libraryDependencies in ThisBuild += "org.scalamacros" % "quasiquotes" % "2.0.0-M3" cross CrossVersion.full

// Add the macro-paradise compiler plugin: This is needed to use quasiquotes
// and other extended macro features.
// This dependency is only needed at compile time: the generated code does not
// need any additional support library.
libraryDependencies in ThisBuild += compilerPlugin("org.scalamacros" % "paradise" % "2.0.0-M3" cross CrossVersion.full)
//Most documentation uses addCompilerPlugin(dependency), but that's equivalent to the following:
//  libraryDependencies += compilerPlugin(dependency)
//We instead use compilerPlugin to be able to specify the scope.
//Strictly speaking, this compiler plugin is only needed for the macros project; however, I chose to make it available everywhere for simplicity.

// To enable debugging for macros, you need to adjust compiler options.
// > set scalacOptions in ThisBuild += "-Ymacro-debug-lite"
