package Lab11

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.library.collection.Container
// import com.cra.figaro.library.atomic.discrete.{FromRange, Uniform}
import com.cra.figaro.library.atomic.continuous.Uniform
import com.cra.figaro.library.atomic.continuous.Normal
// import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.algorithm.factored.beliefpropagation.BeliefPropagation

object pres {
	def main(args: Array[String]) {
		//a)
		val president = Flip(1.0/40000000.0) //P(POTUS) 
		val leftHandedPresidents = Flip(0.5) //P(LH|POTUS)
		val leftHandedNotPresidents = Flip(0.1) //P(LH|!POTUS)
		
		val leftHanded = CPD(president,
		(false) -> Flip(0.1),
		(true) -> Flip(0.5))

		leftHanded.observe(true)
		val alg1 = VariableElimination(president)
		alg1.start()
		println("a) VE - Probability of becaming president given that he or she is left-handed: " + alg1.probability(president, true))
		alg1.stop()

		println("BeliefPropagation: " + BeliefPropagation.probability(president, true))

		val alg11 = Importance(40000, president)
		alg11.start()
		println("Importance: " + alg11.probability(president, true))
		alg11.stop()

		println()
		leftHanded.unobserve()
		//

		//b)
		val wentToHarvardPresidents = Flip(0.15) // P(HU|POTUS)
		val wentToHarvardNotPresidents = Flip(0.0005) // P(HU|!POTUS)

		val wentToHarvard = CPD(president,
		(false) -> Flip(0.0005),
		(true) -> Flip(0.15))

		wentToHarvard.observe(true)
		val alg2 = VariableElimination(president)
		alg2.start()
		println("b) VE - Probability of becaming president given that he or she went to Harvard: " + alg2.probability(president, true))
		alg2.stop()

		println("BeliefPropagation: " + BeliefPropagation.probability(president, true))

		val alg21 = Importance(40000, president)
		alg21.start()
		println("Importance: " + alg21.probability(president, true))
		alg21.stop()

		println()
		wentToHarvard.unobserve()
		//
		
		//c)
		leftHanded.observe(true)
		wentToHarvard.observe(true)

		val alg3 = VariableElimination(president)
		alg3.start()
		println("c) VE - Probability of becaming president given that he or she is left-handed and went to Harvard: " + alg3.probability(president, true))
		alg3.stop()

		println("BeliefPropagation: " + BeliefPropagation.probability(president, true))
		
		val alg31 = Importance(40000, president)
		alg31.start()
		println("Importance: " + alg31.probability(president, true))
		alg31.stop()
		//
	}
}