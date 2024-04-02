package org.ejemploficheros

import kotlin.jvm.JvmStatic
import java.io.File

/*
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

/*
En Kotlin, cuando tenemos múltiples funciones main en nuestro proyecto, el punto de entrada del programa depende de
cómo lo ejecutemos. Esto se debe a que cada función main puede servir como un punto de entrada potencial para nuestra
aplicación.

En el caso de tener múltiples puntos de entrada, necesitamos especificar cuál de ellos deseamos usar al momento de
ejecutar nuestro programa.


*/