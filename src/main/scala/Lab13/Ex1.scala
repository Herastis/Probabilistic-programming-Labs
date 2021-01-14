package Lab13

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.algorithm.sampling.{MetropolisHastingsAnnealer}
import com.cra.figaro.algorithm.factored.beliefpropagation.{BeliefPropagation, MPEBeliefPropagation}
import com.cra.figaro.algorithm.factored.{VariableElimination, MPEVariableElimination}

object Ex13 {
	abstract class State {
		val confident: Element[Boolean]
		def possession: Element[Boolean] =
		If(confident, Flip(0.7), Flip(0.3))
	}

	class InitialState() extends State {
		val confident = Flip(0.4)
	}

	class NextState(current: State) extends State {
		val confident =
		If(current.confident, Flip(0.6), Flip(0.3))
	}

	// produce a state sequence in reverse order of the given length
	def stateSequence(n: Int): List[State] = {
		if (n == 0) List(new InitialState())
		else {
				val last :: rest = stateSequence(n - 1)
				new NextState(last) :: last :: rest
		}
	}
	// unroll the hmm and measure the amount of time to infer the last hidden state

	def timing(obsSeq: List[Boolean]) = {
		Universe.createNew() // ensures that each experiment is separate
		val stateSeq = stateSequence(obsSeq.length)
		for { i <- 0 until obsSeq.length } {
			stateSeq(i).possession.observe(obsSeq(obsSeq.length - 1 - i))
		}

		val alg = VariableElimination(stateSeq(0).confident)
		val time0 = System.currentTimeMillis()
		alg.start()
		val time1 = System.currentTimeMillis()
		(time1 - time0) / 1000.0 // inference time in seconds

		val alg1 = MPEVariableElimination()
		alg1.start()
		println("MPEVariableElimination: ")
		for { i <- 0 until stateSeq.length - 1 } {
			println(alg1.mostLikelyValue(stateSeq(i).confident), obsSeq(obsSeq.length - i - 1))
		}
		alg1.kill()

		val alg2 = MPEBeliefPropagation(10)
		alg2.start()
		println("MPEBeliefPropagation: ")
		for { i <- 0 until stateSeq.length - 1 } {
			println(alg2.mostLikelyValue(stateSeq(i).confident), obsSeq(obsSeq.length - i - 1))
		}
		alg2.kill()

		val alg3 = MetropolisHastingsAnnealer(ProposalScheme.default, Schedule.default(3.0))
		alg3.start()
		println("MetropolisHastingsAnnealer: ")
		for { i <- 0 until stateSeq.length - 1 } {
			println(alg3.mostLikelyValue(stateSeq(i).confident), obsSeq(obsSeq.length - i - 1))
		}
		alg3.kill()
	}
	
	def main(args: Array[String]) {
		val steps = 5
		val obsSeq = List.fill(steps)(scala.util.Random.nextBoolean())
		println(steps + ": " + timing(obsSeq))
	}
}