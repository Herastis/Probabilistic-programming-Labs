package Lab2

abstract class Persoana (nume: String, prenume: String) {
    var nume: String= " "
    var prenume: String= " "
}

class Student(nume: String, prenume: String, an: Integer, materii: Array[(String, Integer)]) extends Persoana (nume: String, prenume: String) {
    //private nota = 0
    //def getNota(materii: String) = nota
    //def setNota
}

class Profesor(nume: String, prenume: String, materie: String) extends Persoana (nume: String, prenume: String) {
}
  
// Creating object 
object Ex1  
{ 
    // Main method  
    def main(args: Array[String])  
    { 
        // Class object  
        var obj = new Student("Tomescu", "Tudor" , 3 , Array(("SD",5),("IP",7)))  
        println("Student nume: " + obj.nume)  
    }  
} 