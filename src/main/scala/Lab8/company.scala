package Lab8

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.library.collection.Container
import com.cra.figaro.library.atomic.continuous.Uniform

//research and development
class RD {
	val state = Uniform(0.0, 1.0)
}

class Production(research: RD) {
	val state = Apply(research.state, 
				(research: Double) => 
					if (research < 0.5) 0.3
					else 0.6)
}

class Sales(production: Production) {
	val state = Apply(production.state,
				(production: Double) =>
					if (production < 0.4) 0.4
					else 0.7)
}

class Finance(sales: Sales) {
	val state = Apply(sales.state,
				(sales: Double) =>
					if (sales < 0.7) 0.3
					else 0.6)
}
//Human Resources
class HR(finance: Finance) {
	val state = Apply(finance.state,
				(finance: Double) =>
					if (finance < 0.4) 0.2
					else 0.8)
}

class Company(hr: HR) {
	val state = Apply(hr.state,
				(hr: Double) =>
					if (hr < 0.3) 0.4
					else 0.7)
}

object lab8 {
	def greaterThan04(production: Double): Boolean = {
		if(production >= 0.4)
			return true
		return false
	}

	def main(args:Array[String]) {
		val research = new RD
		println("Research and development state: " + research.state)
		val production = new Production(research)
		println("Production state: " + production.state)
		val sales = new Sales(production)
		println("Sales state: " + sales.state)
		val finance = new Finance(sales)
		println("Finance state: " + finance.state)
		val hr = new HR(finance)
		println("Human Resources state: " + hr.state)

		research.state.observe(0.2)
		val alg = VariableElimination(production.state)
		alg.start()
		println("Production state greather than 0.4: " + alg.probability(production.state, greaterThan04 _))
		alg.stop()
	}
}