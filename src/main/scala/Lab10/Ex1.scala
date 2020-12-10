package Lab10

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.library.collection.Container
import com.cra.figaro.library.atomic.continuous.Uniform
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.algorithm.sampling.Importance

object lab10 {
	val capital: Array[Element[Double]] = Array.fill(10)(Constant(100))
	val profit: Array[Element[Double]] = Array.fill(10)(Constant(0))
	val investment: Array[Element[Double]] = Array.fill(10)(Constant(0))

	def transition(previousCapital: Double): (Element[Double, Double, Double]) = {
			val investment = previousCapital * 0.8
			val profit = investment * 0.6
			val newCapital = previousCapital + profit - investment
			^^(investment, profit, newCapital)
	} 

  	for { i <- 1 until 10 } {
	    val newState = Chain(capital(i), (c: Double) => transition(c))
	    investment(i) = 
	    profit(i) = 
	    capital(i) = 
  	}

	def main(args: Array[String]) {
		for { i <- 1 until 10 } {	
		}
	}
}