package io.arct.ftclib.bindings

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.squareup.kotlinpoet.*
import io.arct.ftclib.eventloop.LinearOperationMode
import io.arct.ftclib.eventloop.OperationMode
import java.io.File
import java.util.Collections
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

class BindOperationModeProcessor : AbstractProcessor() {
    override fun process(set: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        roundEnv.getElementsAnnotatedWith(OperationMode.Bind::class.java).forEach {
            if (it.kind != ElementKind.CLASS) {
                processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Only classes can be used with the OperationMode.Bind annotation")
                return true
            }

            generate(it as TypeElement)
        }

        return true
    }

    private fun generate(element: TypeElement) {
        val name = element.simpleName.toString()
        val fileName = "${name}_Generated"
        val pack = processingEnv.elementUtils.getPackageOf(element).toString()

        val annotation = element.getAnnotation(OperationMode.Bind::class.java)

        val boundAnnotation = when (annotation.type) {
            OperationMode.Type.Autonomous -> Autonomous::class.java
            OperationMode.Type.Operated -> TeleOp::class.java
            OperationMode.Type.Disabled -> Disabled::class.java
            else -> return run { processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Could not find type for operation mode $name!") }
        }

        val opModeType = when (element.superclass.asTypeName()) {
            OperationMode::class.asTypeName() -> OpModeBinding::class.asTypeName()
            LinearOperationMode::class.asTypeName() -> LinearOpModeBinding::class.asTypeName()
            else -> return run {
                processingEnv.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    "${element.simpleName} is not an operation mode!"
                )
            }
        }

        val generated = FileSpec.builder(pack, fileName)
            .addType(
                TypeSpec.classBuilder(fileName)
                    .addAnnotation(
                        AnnotationSpec.builder(boundAnnotation)
                            .addMember("name = %S", if (annotation.name != "") annotation.name else name)
                            .addMember("group = %S", annotation.group)
                            .build()
                    )
                    .superclass(opModeType)
                    .addFunction(
                        FunSpec.builder("getOperationMode")
                            .returns(element.superclass.asTypeName())
                            .addModifiers(KModifier.PROTECTED, KModifier.OVERRIDE)
                            .addStatement("return $name()")
                            .build()
                    ).build()
            ).build()

        val dir = processingEnv.options["kapt.kotlin.generated"] ?: return processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Can't find the target directory for generated Kotlin files.")
        val file = File(dir, fileName)

        file.parentFile.mkdirs()

        generated.writeTo(file)
    }

    override fun getSupportedAnnotationTypes(): Set<String> =
        Collections.singleton(OperationMode.Bind::class.java.canonicalName)

    override fun getSupportedSourceVersion(): SourceVersion =
        SourceVersion.latestSupported()
}