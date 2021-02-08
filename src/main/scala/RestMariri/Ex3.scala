package Marire

import scala.collection._
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.continuous.Beta
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.experimental.normalproposals.Normal
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.algorithm.factored._


object Markov {
  def main(args: Array[String])
  {

    //A) clasă abstractă pentru modelul markov
    abstract class State {
      var stare = Array[Element[Boolean]] = Array.fill(chapters)(Constant("buna"))
    }

    //B) clasa pentru initial state:
    class initialState() extends State {
      // atribuim probabilitati initiale pentru variabila stare
      stare(0) = Select(0.721 -> 'buna, 0.202 -> 'nu prea buna, 0.067 -> 'bolnav, 0.1 -> 'decedat)
    }

    //C) clasa ce creează o stare următoare, având starea curentă dată ca parametru.
    class NextState(current: State, iteration: Int) extends State {
      // valori pentru stare
      stare(iteration) = CPD(stare(chapter - 1),
        'buna -> Select(0.721 -> 'buna, 0.202 -> 'nu prea buna, 0.067 -> 'bolnav,  0.1 -> 'decedat),
        'nu prea buna -> Select(0 -> 'buna, 0.581 -> 'nu prea buna, 0.407 -> 'bolnav,   0.012 -> 'decedat),
        'bolnav -> Select(0 -> 'buna, 0 -> 'nu prea buna, 0.75 -> 'bolnav, 0.25 -> 'decedat))
        'decedat -> Select(0 -> 'buna, 0 -> 'nu prea buna, 0 -> 'bolnav, 1.0 -> 'decedat))
    }

  }
}