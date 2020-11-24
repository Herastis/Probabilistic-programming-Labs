package Partial

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.algorithm.factored._
object alarma{

	//10% sanse sa uiti sa setezi alarma
  val seteziAlarma = Flip(0.9)
  val autobuzIntarzie = Flip(0.2)

  val trezitLaTimp = CPD(seteziAlarma,
      (true) -> Flip(0.9), //sanse sa te trezesti fara sa adormi la loc
      (false) -> Flip(0.1) // un caz din 10 te trezesti devreme
      )


  val laTimp = CPD(trezitLaTimp, autobuzIntarzie,
      (true, true) -> Flip(0.2),
      (true, false) -> Flip(0.9),
      (false, true) -> Flip(0.1),
      (false, false) -> Flip(0.3)
      )

  laTimp.observe(true)

  val alg = VariableElimination(seteziAlarma, autobuzIntarzie)
  alg.start()

      println("Probabilitatea sa ajungi devreme daca ai adormit: " + alg.probability(trezitLaTimp, false))
      println("Probabilitatea sa iti fi pus alarma, daca ajungi devreme la serviciu: " + alg.probability(laTimp, true))
      println("Probabilitatea sa adormi: " + alg.probability(seteziAlarma, false))
    
  alg.kill

}
