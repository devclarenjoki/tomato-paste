package com.codility.mvp

import io.reactivex.disposables.CompositeDisposable

class Presenter(view: ListContract.View, elementsProvider: ElementsProvider, schedulerFacade: SchedulerFacade) {
  val disposables = CompositeDisposable()

  init {
    disposables.add(
        elementsProvider.loadElements()
            .subscribeOn(schedulerFacade.background)
            .observeOn(schedulerFacade.main)
            .subscribe()
    )
  }
}

===============================================


Compiler output:
========================== STDOUT ==========================


> Configure project :app
[configuration ':app:annotationProcessor', configuration ':app:api', configuration ':app:apiDependenciesMetadata', configuration ':app:apiElements', configuration ':app:archives', configuration ':app:compileClasspath', configuration ':app:compileOnly', configuration ':app:compileOnlyDependenciesMetadata', configuration ':app:default', configuration ':app:implementation', configuration ':app:implementationDependenciesMetadata', configuration ':app:intransitiveDependenciesMetadata', configuration ':app:kotlinCompilerClasspath', configuration ':app:kotlinCompilerPluginClasspath', configuration ':app:kotlinCompilerPluginClasspathMain', configuration ':app:kotlinCompilerPluginClasspathTest', configuration ':app:kotlinKlibCommonizerClasspath', configuration ':app:kotlinNativeCompilerPluginClasspath', configuration ':app:kotlinScriptDef', configuration ':app:kotlinScriptDefExtensions', configuration ':app:runtimeClasspath', configuration ':app:runtimeElements', configuration ':app:runtimeOnly', configuration ':app:runtimeOnlyDependenciesMetadata', configuration ':app:sourceArtifacts', configuration ':app:testAnnotationProcessor', configuration ':app:testApi', configuration ':app:testApiDependenciesMetadata', configuration ':app:testCompileClasspath', configuration ':app:testCompileOnly', configuration ':app:testCompileOnlyDependenciesMetadata', configuration ':app:testImplementation', configuration ':app:testImplementationDependenciesMetadata', configuration ':app:testIntransitiveDependenciesMetadata', configuration ':app:testKotlinScriptDef', configuration ':app:testKotlinScriptDefExtensions', configuration ':app:testRuntimeClasspath', configuration ':app:testRuntimeOnly', configuration ':app:testRuntimeOnlyDependenciesMetadata']

> Task :clean
> Task :app:clean

BUILD SUCCESSFUL in 5s
2 actionable tasks: 2 executed

> Configure project :app
[configuration ':app:annotationProcessor', configuration ':app:api', configuration ':app:apiDependenciesMetadata', configuration ':app:apiElements', configuration ':app:archives', configuration ':app:compileClasspath', configuration ':app:compileOnly', configuration ':app:compileOnlyDependenciesMetadata', configuration ':app:default', configuration ':app:implementation', configuration ':app:implementationDependenciesMetadata', configuration ':app:intransitiveDependenciesMetadata', configuration ':app:kotlinCompilerClasspath', configuration ':app:kotlinCompilerPluginClasspath', configuration ':app:kotlinCompilerPluginClasspathMain', configuration ':app:kotlinCompilerPluginClasspathTest', configuration ':app:kotlinKlibCommonizerClasspath', configuration ':app:kotlinNativeCompilerPluginClasspath', configuration ':app:kotlinScriptDef', configuration ':app:kotlinScriptDefExtensions', configuration ':app:runtimeClasspath', configuration ':app:runtimeElements', configuration ':app:runtimeOnly', configuration ':app:runtimeOnlyDependenciesMetadata', configuration ':app:sourceArtifacts', configuration ':app:testAnnotationProcessor', configuration ':app:testApi', configuration ':app:testApiDependenciesMetadata', configuration ':app:testCompileClasspath', configuration ':app:testCompileOnly', configuration ':app:testCompileOnlyDependenciesMetadata', configuration ':app:testImplementation', configuration ':app:testImplementationDependenciesMetadata', configuration ':app:testIntransitiveDependenciesMetadata', configuration ':app:testKotlinScriptDef', configuration ':app:testKotlinScriptDefExtensions', configuration ':app:testRuntimeClasspath', configuration ':app:testRuntimeOnly', configuration ':app:testRuntimeOnlyDependenciesMetadata']

> Task :app:processResources NO-SOURCE

> Task :app:compileKotlin FAILED
1 actionable task: 1 executed


========================== STDERR ==========================

e: /task/app/src/main/java/com/codility/mvp/ListContract.kt: (5, 11): Redeclaration: ListContract
e: /task/app/src/main/java/com/codility/mvp/ListContract.kt: (19, 11): Redeclaration: ElementsProvider
e: /task/app/src/main/java/com/codility/mvp/Presenter.kt: (20, 43): Unresolved reference: getElements
e: /task/app/src/main/java/com/codility/mvp/Presenter.kt: (24, 19): Cannot infer a type for this parameter. Please specify it explicitly.
e: /task/app/src/main/java/com/codility/mvp/Presenter.kt: (26, 30): Unresolved reference: showEmpty
e: /task/app/src/main/java/com/codility/mvp/Presenter.kt: (28, 30): Unresolved reference: showElements
e: /task/app/src/main/java/com/codility/mvp/Presenter.kt: (31, 19): Cannot infer a type for this parameter. Please specify it explicitly.
e: /task/app/src/main/java/com/codility/mvp/Presenter.kt: (32, 36): Too many arguments for public abstract fun showError(): Unit defined in com.codility.mvp.ListContract.View
e: /task/app/src/main/java/com/codility/mvp/Presenter.kt: (48, 11): Redeclaration: ListContract
e: /task/app/src/main/java/com/codility/mvp/Presenter.kt: (57, 11): Redeclaration: ElementsProvider
e: /task/app/src/main/java/com/codility/mvp/Presenter.kt: (61, 11): Redeclaration: SchedulerFacade
e: /task/app/src/main/java/com/codility/mvp/SchedulerFacade.kt: (5, 11): Redeclaration: SchedulerFacade

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:compileKotlin'.
> A failure occurred while executing org.jetbrains.kotlin.compilerRunner.GradleCompilerRunnerWithWorkers$GradleKotlinCompilerWorkAction
   > Compilation error. See log for more details

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 9s


Exit code: 1
