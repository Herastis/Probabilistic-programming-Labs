package Lab4 

import com.cra.figaro.language._
import com.cra.figaro.library.compound._
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.library.atomic.discrete._

object GoodMood {
    val sunnyDaysInMonth = Binomial(30, 0.2) // In 30 days, how many of them will be sunny

    val monthQuality = Apply(sunnyDaysInMonth,(i: Int) => {
        if (i > 10) "good" 
        else if (i > 5) "average"
        else "poor"
    })

    val sunnyToday = Flip(0.2)
    val goodMood = Chain(monthQuality, sunnyToday,(quality: String, sunny: Boolean) =>
        if (sunny) {
            if (quality == "good") Flip(0.9)
            else if (quality == "average") Flip(0.7)
            else Flip(0.4)
        } else {
            if (quality == "good") Flip(0.6)
            else if (quality == "average") Flip(0.3)
            else Flip(0.05)
        }
    )

    def main(args: Array[String]) {
        println(VariableElimination.probability(goodMood, true))
        
        sunnyDaysInMonth.setCondition((i: Int) => i > 8)
        println(VariableElimination.probability(goodMood, true))
    }
}