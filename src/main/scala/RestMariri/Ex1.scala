package Marire

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.algorithm.sampling.{MetropolisHastingsAnnealer}
import com.cra.figaro.algorithm.factored.beliefpropagation.{BeliefPropagation, MPEBeliefPropagation}
import com.cra.figaro.algorithm.factored.{VariableElimination, MPEVariableElimination}


//Ex 1
object Vreme {
	//Cate saptamani vor fi cu ninsoare in luna februarie
    val zileNinsoare = Binomial(28, 0.6) //Zile cu ninsoare in luna februarie
	
	//Determinarea tipului de vreme in functie de zilele la rand in care a nins
    val calitateSaptamana = Apply(zileNinsoare,(i: Int) => {
        if (i > 5) "prea multa ninsoare" 
		else if (i > 2) "prea putina ninsoare"
        else "normala"
    })

    val ninsoareAzi = Flip(0.6)
    val Vreme = Chain(calitateSaptamana, ninsoareAzi,(calitateSaptamana: String, ninsoare: Boolean) =>
        if (ninsoare) {
            if (calitateSaptamana == "prea multa ninsoare") Flip(0.9)
            else if (calitateSaptamana == "prea putina ninsoare") Flip(0.7)
            else Flip(0.4)
        } else {
            if (calitateSaptamana == "prea multa ninsoare") Flip(0.6)
            else if (calitateSaptamana == "prea putina ninsoare") Flip(0.3)
            else Flip(0.05)
        }
    )

	 def main(args: Array[String]) {
		println("Probabilitatea ca vremea sa fie normala: ")
        println(VariableElimination.probability(calitateSaptamana, "normala"))
        
        zileNinsoare.setCondition((i: Int) => i > 8)
        println(VariableElimination.probability(Vreme, true))
    }

}
