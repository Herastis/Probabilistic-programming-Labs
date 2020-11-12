package Lab7

import com.cra.figaro.language._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.collection.Container
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.library.atomic.discrete.{FromRange, Uniform}

object golf{
	def greaterThan80(score: Double): Boolean = {
		if(score > 80.0)
			return true
		return false
	}

	def main(args:Array[String]){
		var par = Array(3.0, 4.0, 5.0, 4.0, 5.0, 3.0, 5.0, 3.0, 5.0, 3.0, 4.0, 5.0, 5.0, 3.0, 3.0, 5.0, 4.0, 5.0)
		var skill = Uniform(0, 8.0/13.0)
		var shots = List.tabulate(18)(n => Select(
			Apply(skill, (skill: Double) => skill/8) -> (par(n) - 2), 
			Apply(skill, (skill: Double) => skill/2) -> (par(n) - 1), 
			Apply(skill, (skill: Double) => skill) -> (par(n)),
			Apply(skill, (skill: Double) => 4.0/5.0 * (1 - 13.0*skill/8.0)) -> (par(n) + 1), 
			Apply(skill, (skill: Double) => 1.0/5.0 * (1 - 13.0*skill/8.0)) -> (par(n) + 2)))

		val score: Element[Double] = Container(shots:_*).reduce(_ + _)
		val alg = Importance(1000, score)
		alg.start()
		println("Probabilitatea ca scorul mai mare decat 80:")
		println(alg.probability(score, greaterThan80 _))

		// skill.setCondition((i: Double) => i > 0.3)
		// val score2: Element[Double] = Container(shots:_*).reduce(_ + _)
		// println(alg.probability(score2, greaterThan80 _))
		}
}