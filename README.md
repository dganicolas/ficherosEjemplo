# Compilación de archivos en Kotlin

## 1. Distintos puntos de entrada en un programa

En Kotlin, cuando tenemos múltiples funciones `main` en nuestro proyecto, el punto de entrada del programa depende de cómo lo ejecutemos. Esto se debe a que cada función `main` puede servir como un punto de entrada potencial para nuestra
aplicación.

En el caso de tener múltiples puntos de entrada, necesitamos especificar cuál de ellos deseamos usar al momento de ejecutar nuestro programa.

Vamos a trabajar con el siguiente código desarrollado por completo en el fichero `Main.kt`:

```kotlin
package org.ejemploficheros

import kotlin.jvm.JvmStatic
import java.io.File

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
```

Si ambas funciones `main` están dentro de un único archivo llamado `Main.kt` en Kotlin, la organización de nuestro código determinará cómo puedes ejecutar cada función desde la línea de comandos. Teniendo en cuenta que una de las funciones `main` está dentro de un objeto llamado `PruebasArchivos` y la otra es una función `main` global, a continuación mostramos cómo pueden ejecutarse...

### 1.1. **Ejecutar la función `main` dentro del objeto `PruebasArchivos`:**

Dado que esta función `main` está dentro de un objeto, Kotlin compila esta función a un método estático de una clase generada. El nombre de la clase generada sigue el formato `<NombreDelArchivo>Kt`, donde `<NombreDelArchivo>` es el nombre del archivo Kotlin sin la extensión `.kt`. Por lo tanto, si tu archivo se llama `Main.kt`, el nombre de la clase generada será `MainKt`.

Sin embargo, debido a que la función `main` está dentro de un objeto, necesitas usar el nombre del objeto para acceder a ella. En Java, esto se traduciría a una clase con el nombre del objeto. Kotlin nombra estas clases basándose en el objeto, por lo que no es necesario agregar `Kt` después del nombre del objeto. Así que para ejecutar esta función `main`, usaríamos el comando:

```shell
kotlin org.ejemploficheros.PruebasArchivos
```

Aquí, `org.ejemploficheros` es el nombre del paquete, y `PruebasArchivos` es el nombre del objeto que contiene tu función `main`. No necesitamos agregar `Kt` al final porque estamos accediendo a través de un objeto.

### 1.2. **Ejecutar la función `main` global:**

La función `main` global que no está dentro de ningún objeto o clase se compilará en una clase que lleva el nombre del archivo seguido de `Kt`. Por tanto, para ejecutar esta función `main`, necesitamos especificar esta clase generada en el comando.

El comando para ejecutar la función `main` global sería:

```shell
kotlin org.ejemploficheros.MainKt
```

En este caso, `MainKt` es el nombre de la clase generada a partir del archivo `Main.kt`, y `org.ejemploficheros` indica el paquete en el que se encuentra el archivo.


## 2. Empaquetar nuestra aplicación en un archivo JAR

Cuando usamos Gradle para empaquetar nuestra aplicación Kotlin en un archivo JAR, podemos configurar el archivo `MANIFEST.MF` directamente desde el archivo `build.gradle.kts` (ya que estamos utilizando la sintaxis de Kotlin para nuestros scripts de Gradle). Gradle nos permite especificar las propiedades del manifiesto que deseamos incluir en el archivo JAR final, incluido el punto de entrada de la aplicación mediante la especificación de la clase `Main-Class`.

Si tenemos múltiples puntos de entrada y deseamos crear un JAR ejecutable para uno específico, deberemos decidir cuál de ellos será el punto de entrada por defecto cuando el JAR se ejecute. Solo podemos especificar una clase `Main-Class` en el `MANIFEST.MF` para un JAR dado.

A continuación vamos a configurar el archivo `MANIFEST.MF` en nuestro proyecto Gradle para especificar la clase principal:

### 2.1. Para proyectos utilizando el archivo `build.gradle.kts` (Kotlin DSL)

```kotlin
tasks.jar {
    manifest {
        attributes("Main-Class" to "org.ejemploficheros.MainKt")
    }
}
```

Cuando ejecutemos `./gradlew jar`, Gradle generará un archivo JAR en `build/libs` que utiliza esta configuración del manifiesto.

Por defecto va a generar un nombre referido al nombre base del proyecto `EjemploFicheros`, un número de versión `1.0-SNAPSHOT` seguido finalmente de la extensión `.jar`.

Para cambiar el nombre del archivo JAR, podemos ajustar las propiedades `archiveFileName`, `archiveBaseName`, o `archiveVersion` dentro de la configuración de la tarea jar. Aquí tienes un ejemplo que establece un nombre de archivo específico:

```kotlin
tasks.jar {
    archiveFileName.set("EjemploFicheros.jar")
}
```

Este código configura la tarea jar para generar un archivo llamado `EjemploFicheros.jar`, ignorando el nombre base del proyecto y la versión.

Si queremos mantener el uso de `baseName` y `version` pero personalizarlos, podemos hacerlo así:

```kotlin
tasks.jar {
    archiveBaseName.set("EjemploFicheros")
    archiveVersion.set("1.0.0")
    archiveFileName.set("${archiveBaseName.get()}-${archiveVersion.get()}.jar")
}
```

Estos ejemplos personalizan el nombre base y la versión del archivo JAR y luego configuran archiveFileName para usar estos valores.

Debemos recordar aplicar estos cambios en nuestro archivo build.gradle.kts, y luego ejecutar `./gradlew jar` nuevamente para generar el archivo JAR con el nuevo nombre.

Si deseamos crear diferentes JARs para diferentes puntos de entrada, tendremos que configurar tareas Gradle personalizadas para cada JAR que deseamos crear, especificando un `Main-Class` diferente para cada uno.

### 2.2. Ejemplo de tarea Gradle personalizada para múltiples JARs

Si necesitamos generar múltiples archivos JAR cada uno con un punto de entrada diferente, podemos definir tareas personalizadas en Gradle para cada uno:

```kotlin
val jarWithPruebasArchivos by tasks.creating(Jar::class) {
    archiveBaseName.set("EjemploFicheros-PruebasArchivosApp")
    manifest {
        attributes("Main-Class" to "org.ejemploficheros.PruebasArchivos")
    }
    from(sourceSets.main.get().output)
}

val jarWithMain by tasks.creating(Jar::class) {
    archiveBaseName.set("EjemploFicheros-MainApp")
    manifest {
        attributes("Main-Class" to "org.ejemploficheros.MainKt")
    }
    from(sourceSets.main.get().output)
}
```

En este ejemplo, definimos dos tareas de tipo `Jar`: `jarWithPruebasarchivos` y `jarWithMain`. Cada tarea establece un nombre base diferente para el archivo JAR generado y especifica una clase `Main-Class` distinta en el manifiesto del JAR. La propiedad `archiveBaseName.set()` define el nombre del archivo JAR sin la extensión, y `attributes("Main-Class" to "...")` se usa para especificar la clase principal.

Para construir los JARs específicos según estas tareas personalizadas, ejecutaríamos los comandos Gradle correspondientes en la línea de comandos o en la terminal. Por ejemplo:

```shell
./gradlew jarWithPruebasArchivos
```

y

```shell
./gradlew jarWithMain
```

Esto generará los archivos JAR especificados en el directorio `build/libs` de nuestro proyecto con los nombres y puntos de entrada configurados.

## 3. Ubicación de los ficheros compilados

En IntelliJ IDEA, por defecto, los ficheros compilados (archivos `.class` en el caso de proyectos Java y Kotlin) se generan en el directorio `out` dentro de la raíz del proyecto. Dentro de este directorio, la estructura se divide en subdirectorios para separar los archivos de compilación de producción y los de prueba. La estructura típica se ve así:

- `out`
    - `production`
        - `<Nombre del proyecto o módulo>`
    - `test`
        - `<Nombre del proyecto o módulo>`

Por lo tanto, si nuestro proyecto o módulo se llama `EjemploFicheros`, encontraremos los archivos compilados en `out/production/EjemploFicheros` para el código de producción y en `out/test/MiProyecto` para el código de las pruebas.

Sin embargo, esta ubicación puede ser configurada y variar dependiendo de la configuración específica de tu proyecto o del uso de sistemas de construcción personalizados como Gradle o Maven. 

Si estamos usando Gradle o Maven, IntelliJ IDEA utilizará la estructura de directorios y las convenciones de estos sistemas de construcción, que típicamente colocan los archivos compilados en directorios como `build` (para Gradle) o `target` (para Maven) dentro del directorio del módulo correspondiente:

- Para Gradle, los archivos compilados suelen estar en `build/classes/kotlin/main` (para Kotlin) o `build/classes/java/main` (para Java) para el código de producción y `build/classes/kotlin/test` (para Kotlin) o `build/classes/java/test` (para Java) para el código de prueba. Los archivos JAR generados suelen estar en `build/libs`.


- Para Maven, los archivos compilados se encuentran generalmente en `target/classes` para el código de producción y `target/test-classes` para el código de prueba.

Para encontrar la ubicación exacta en nuestro proyecto, podemos revisar la configuración del proyecto en IntelliJ IDEA o mirar el archivo de configuración de nuestro sistema de construcción (como `build.gradle` para Gradle o `pom.xml` para Maven).