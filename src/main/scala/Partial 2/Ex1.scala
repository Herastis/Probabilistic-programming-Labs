/*
package Partial2

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.algorithm.sampling.{MetropolisHastingsAnnealer}
import com.cra.figaro.algorithm.factored.beliefpropagation.{BeliefPropagation, MPEBeliefPropagation}
import com.cra.figaro.algorithm.factored.{VariableElimination, MPEVariableElimination}


class Start (ins:Insorit, inn:Innorat, pl:Ploios) {
	ins = Flip(0.5)
	inn = Flip(0.3)
	pl = Flip(0.2)
}


class Insorit(inn: Innorat, pl: Ploios) {
	ins = Flip(0.6)
	val iauUmbrela = if(Flip(0.15), Flip(0.85))
}

class Ploios(ins:Insorit, inn:Innorat) {
	val iauUmbrela = if(Flip(0.75), Flip(0.25))
}

class Innorat(ins:Insorit,pl:Ploios) {
	val iauUmbrela = if(Flip(0.65), Flip(0.35))
}


object markov {
	
	def main(args: Array[String]) {
		val steps = 5
		val obsSeq = List.fill(steps)(scala.util.Random.nextBoolean())
		println(steps + ": " + timing(obsSeq))
	}
}
*/