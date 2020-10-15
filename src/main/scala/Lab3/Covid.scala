package Lab3 

import com.cra.figaro.algorithm.factored._
import com.cra.figaro.language._
import com.cra.figaro.library.compound._

object Covid{
    def main(args: Array[String]){

     val febra = Flip(0.01)
     val tuse = Flip(0.0001)

     val covid = CPD(febra, tuse,
        (false, false) -> Flip(0.05),
        (false, true) -> Flip(0.2),
        (true, false) -> Flip(0.4),
        (true, true) -> Flip(0.6))

    
     val cofirmedCovid = CPD(covid,
        false -> Flip (0.03),
        true -> Flip(0.8))

    cofirmedCovid.observe(true)

    val alg = VariableElimination(febra,tuse)
    alg.start()

        println("Probability of having Covid with just fever: " + alg.probability(febra, true))
        println("Probability of having Covid with just coughing: " + alg.probability(tuse, true))
    
    alg.kill

    println()

    febra.observe(false)
    tuse.observe(false)

    val asimptomatic = CPD(covid,
        false -> Flip (0.7),
        true -> Flip (0.2))

    asimptomatic.observe(true)

    val alg1 = VariableElimination(febra,tuse)
    alg1.start()

        println("Probability of having Covid without fever: " + alg1.probability(febra, true))
        println("Probability of having Covid without coughing: " + alg1.probability(tuse, true))

    alg1.kill
    
    } 
}
