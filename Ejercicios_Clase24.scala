
abstract class Animal(val nombre: String)
class Perro(nombre: String) extends Animal(nombre)
class Gato(nombre: String) extends Animal(nombre)

class Caja[A](val contenido: A) {

  def obtener: A = contenido

  def combinar[B](otro: B): (A, B) = (contenido, otro)
}


object Ej2 {
  def seleccionarPrimero[A](primero: A, segundo: A): A = primero
}


object Ej3 {
  def transformar[A, B](valor: A, funcion: A => B): B = funcion(valor)
}


abstract class Vehiculo(val marca: String)
class Automovil(marca: String) extends Vehiculo(marca)
class Motocicleta(marca: String) extends Vehiculo(marca)

class RegistroVehiculo[A <: Vehiculo](val vehiculo: A)


class Contenedor[+A](val valor: A)


trait Procesador[-A] {
  def procesar(valor: A): String
}

class ProcesadorAnimal extends Procesador[Animal] {
  def procesar(valor: Animal): String = s"Procesando animal: ${valor.nombre}"
}


trait Productor[+A] {
  def producir: A
}

trait Consumidor[-A] {
  def consumir(valor: A): String
}

class CriadorPerros extends Productor[Perro] {
  def producir: Perro = new Perro("Rocky")
}

class CriadorGatos extends Productor[Gato] {
  def producir: Gato = new Gato("Michi")
}

class ConsumidorAnimales extends Consumidor[Animal] {
  def consumir(valor: Animal): String = s"Consumiendo a ${valor.nombre}"
}

class ConsumidorGatos extends Consumidor[Gato] {
  def consumir(valor: Gato): String = s"Cuidando al gato ${valor.nombre}"
}


class Resultado[A](val valor: A, val exitoso: Boolean, val mensaje: String)

case class Persona(nombre: String, edad: Int)


object Ejercicios_Clase24 {

  def main(args: Array[String]): Unit = {

    println("=== Ejercicio 1: Caja[A] ===")
    val cajaInt = new Caja(10)
    val cajaString = new Caja("Scala")
    val cajaBoolean = new Caja(true)
    println(s"Caja[Int].obtener      = ${cajaInt.obtener}")
    println(s"Caja[String].obtener   = ${cajaString.obtener}")
    println(s"Caja[Boolean].obtener  = ${cajaBoolean.obtener}")
    println(s"Caja[Int].combinar(\"x\")   = ${cajaInt.combinar("x")}")
    println(s"Caja[String].combinar(2026) = ${cajaString.combinar(2026)}")
    println(s"Caja[Boolean].combinar(3.5) = ${cajaBoolean.combinar(3.5)}")

    println("\n=== Ejercicio 2: seleccionarPrimero ===")
    println(Ej2.seleccionarPrimero(10, 20))
    println(Ej2.seleccionarPrimero("Scala", "Java"))
    println(Ej2.seleccionarPrimero(1.5, 2.5))

    println("\n=== Ejercicio 3: transformar ===")
    val intAString = Ej3.transformar(10, (n: Int) => n.toString)
    val stringALongitud = Ej3.transformar("Scala", (s: String) => s.length)
    val doubleABoolean = Ej3.transformar(3.14, (d: Double) => d > 0)
    println(s"Int -> String:      \"$intAString\"")
    println(s"String -> Int:      $stringALongitud")
    println(s"Double -> Boolean:  $doubleABoolean")

    println("\n=== Ejercicio 4: límite superior ===")
    val regAuto = new RegistroVehiculo(new Automovil("Toyota"))
    val regMoto = new RegistroVehiculo(new Motocicleta("Honda"))
    println(s"Automóvil: ${regAuto.vehiculo.marca}")
    println(s"Motocicleta: ${regMoto.vehiculo.marca}")


    println("\n=== Ejercicio 5: covarianza ===")
    val contenedorPerro: Contenedor[Perro] = new Contenedor(new Perro("Rocky"))
    val contenedorAnimal: Contenedor[Animal] = contenedorPerro 
    println(s"Contenedor[Animal] contiene: ${contenedorAnimal.valor.nombre}")

    println("\n=== Ejercicio 6: contravarianza ===")
    val procesadorPerros: Procesador[Perro] = new ProcesadorAnimal 
    println(procesadorPerros.procesar(new Perro("Rocky")))

    println("\n=== Ejercicio 7: productor y consumidor ===")
    val productorAnimal1: Productor[Animal] = new CriadorPerros
    val productorAnimal2: Productor[Animal] = new CriadorGatos
    println(s"Productor[Animal] (perros): ${productorAnimal1.producir.nombre}")
    println(s"Productor[Animal] (gatos):  ${productorAnimal2.producir.nombre}")

    val consumidorPerros: Consumidor[Perro] = new ConsumidorAnimales
    val consumidorGatos: Consumidor[Gato] = new ConsumidorAnimales
    println(consumidorPerros.consumir(new Perro("Rocky")))
    println(consumidorGatos.consumir(new Gato("Michi")))
    println(new ConsumidorGatos().consumir(new Gato("Garfield")))
   
    println("\n=== Ejercicio 8: Resultado[A] ===")
    val resInt = new Resultado(25, true, "Calculo correcto")
    val resString = new Resultado("Operacion completada", true, "OK")
    val resPersona = new Resultado(Persona("Ana", 30), true, "Persona encontrada")
    println(s"Resultado[Int]:     ${resInt.valor} | ${resInt.exitoso} | ${resInt.mensaje}")
    println(s"Resultado[String]:  ${resString.valor} | ${resString.exitoso} | ${resString.mensaje}")
    println(s"Resultado[Persona]: ${resPersona.valor} | ${resPersona.exitoso} | ${resPersona.mensaje}")
  }
}
