package org.ejemploficheros

import kotlin.jvm.JvmStatic
import java.io.File

/*
1. Anotación @JvmStatic.

La anotación @JvmStatic es específica para interoperar con Java.

Kotlin tiene su propio sistema de clases y objetos, pero cuando queremos asegurarnos de que nuestro código Kotlin sea
fácilmente accesible desde código Java, especialmente en casos donde necesitemos definir un método main o cualquier
método estático, utilizas @JvmStatic.

En el contexto de Java, los métodos estáticos son aquellos que puedes llamar en una clase sin necesidad de crear una
instancia de dicha clase. Kotlin no tiene métodos estáticos de forma nativa porque usa objetos para lograr el mismo
resultado.

Cuando marcamos una función con @JvmStatic dentro de un objeto o un companion object en Kotlin, le indicamos al
compilador de Kotlin que genere bytecode que exponga esa función como un método estático de la clase desde el punto
de vista de Java. Esto significa que el método puede ser llamado desde Java sin necesidad de instanciar la clase que
lo contiene, alineándose con el modelo de clases de Java.

En resumen, la anotación @JvmStatic permite que el método main sea invocado como un punto de entrada estándar por
la JVM (Java Virtual Machine) sin necesidad de crear una instancia del objeto Pruebasarchivos. Es importante si el
código debe ser interoperable con Java o si estás escribiendo una aplicación Kotlin que se ejecuta en un entorno
Java tradicional.

2. Interoperabilidad Kotlin-Java.

En Kotlin, al trabajar con clases de Java que siguen el patrón de diseño "getter" y "setter" para el acceso a
propiedades, se puede acceder a estas mediante una sintaxis de propiedad más simple y directa, que es más natural en
Kotlin. Esto es posible gracias a las convenciones de acceso a propiedades de Kotlin, que automáticamente mapean entre
las llamadas a métodos getter y setter de Java y el acceso a propiedades en Kotlin.

Cuando accedemos a f.parent en Kotlin, el compilador de Kotlin automáticamente lo traduce a una llamada al método
getParent() de la instancia de java.io.File subyacente. De manera similar, si hubiera un método setParent(String parent)
en la clase (lo cual no ocurre con java.io.File, pero es útil como ejemplo), podríamos asignar un valor a esa
"propiedad" con una sintaxis como f.parent = "nuevoValor", y Kotlin traduciría eso a una llamada a setParent("nuevoValor").

Este comportamiento hace que trabajar con código Java en Kotlin sea más idiomático y conciso, permitiendo que el código
Kotlin se sienta más natural y manteniendo las ventajas de la orientación a objetos y encapsulamiento proporcionadas
por los métodos getter y setter en Java.

En resumen, la llamada a f.parent en tu código Kotlin es equivalente a una llamada a f.getParent() debido a la
interoperabilidad de Kotlin con Java y su capacidad para trabajar con las convenciones de nombres de getter y setter
de manera más expresiva.
*/

object PruebasArchivos {
    @JvmStatic
    fun main(args: Array<String>) {
        // Dos rutas absolutas
        val carpetaAbs = File("/home/lionel/fotos")
        val archivoAbs = File("/home/lionel/fotos/albania1.jpg")

        // Dos rutas relativas
        val carpetaRel = File("trabajos")
        val fitxerRel = File("trabajos/documento.txt")

        // Mostremos sus rutas
        mostrarRutas(carpetaAbs)
        mostrarRutas(archivoAbs)
        mostrarRutas(carpetaRel)
        mostrarRutas(fitxerRel)
    }

    fun mostrarRutas(f: File) {
        println("getParent(): " + f.parent)
        println("getName(): " + f.name)
        println("getAbsolutePath(): " + f.absolutePath)
    }
}

fun main() {
    println("Hello World!")
}