package Lab6

object Ex6 {
def tennis(
			probP1ServeWin: Double, probP1Winner: Double, probP1Error: Double,
			probP2ServeWin: Double, probP2Winner: Double, probP2Error: Double):
		Element[Boolean] = {
	def rally(firstShot: Boolean, player1: Boolean): Element[Boolean] = {
	val pWinner =
		if (firstShot && player1) probP1ServeWin
		// se seteaza probabilitatea primului jucator de a castiga, cand el serveste; pWinner = probP1ServeWin
		else if (firstShot && !player1) probP2ServeWin
		// se seteaza probabilitatea celui de al doilea jucator de a castiga, cand el serveste; pWinner = probP2ServeWin
		else if (player1) probP1Winner
		// se seteaza probabilitatea cazului in care player1castiga; pWinner = probP1Winner
		else probP2Winner
		// se seteaza probabilitatea cazului in care player2castiga; pWinner = probP2Winner
	val pError = if (player1) probP1Error else probP2Error
	//pError primeste probabilitatea ca unul dintre jucatori sa gafeze cand mingea e la el; 
	val winner = Flip(pWinner)
	//se atribuie in variabila winner castigatorul
	val error = Flip(pError)
	If(winner, Constant(player1), //player1 castiga
		If(error, Constant(!player1), //player2 castiga
		rally(false, !player1))) //se joaca in continuare (nu s-a servit inca)
	}

	def game(
		p1Serves: Boolean, p1Points: Element[Int],
		p2Points: Element[Int]): Element[Boolean] = {
	val p1WinsPoint = rally(true, p1Serves)
	// p1WinsPoint = 1 daca a castigat, 0 daca a facut o eroare sau se joaca in continuare
	val newP1Points =
		Apply(p1WinsPoint, p1Points, (wins: Boolean, points: Int) =>
			if (wins) points + 1 else points)
			//newP1Points primeste punctele castigate intr-un joc
	val newP2Points =
		Apply(p1WinsPoint, p2Points, (wins: Boolean, points: Int) =>
			if (wins) points else points + 1)
			//newP2Points primeste punctele castigate intr-un joc
	val p1WinsGame =
		Apply(newP1Points, newP2Points, (p1: Int, p2: Int) =>
			p1 >= 4 && p1 - p2 >= 2)
			// pe baza punctelor, se verifica daca player1 a castigat joc
	val p2WinsGame =
		Apply(newP2Points, newP1Points, (p2: Int, p1: Int) =>
			p2 >= 4 && p2 - p1 >= 2)
			// pe baza punctelor, se verifica daca player2 a castigat joc
	val gameOver = p1WinsGame || p2WinsGame
	//gameOver primeste castigatorul unui joc
	If(gameOver, p1WinsGame, game(p1Serves, newP1Points, newP2Points))
	//se incepe un nou joc; se reseteaza variabilele
	}
	

	def play(
		p1Serves: Boolean, p1Sets: Element[Int], p2Sets: Element[Int],
		p1Games: Element[Int], p2Games: Element[Int]): Element[Boolean] = {
	val p1WinsGame = game(p1Serves, Constant(0), Constant(0))
	val newP1Games =
		Apply(p1WinsGame, p1Games, p2Games,
				(wins: Boolean, p1: Int, p2: Int) =>
					if (wins) {
						if (p1 >= 5) 0 else p1 + 1
						// daca castiga 5 jocuri, castiga un set si se reseteaza numarul de jocuri castigate  
					} else {
						if (p2 >= 5) 0 else p1
						// daca castiga 5 jocuri, castiga un set si se reseteaza numarul de jocuri castigate  
					})
	val newP2Games =
		Apply(p1WinsGame, p1Games, p2Games,
				(wins: Boolean, p1: Int, p2: Int) =>
					if (wins) {
						if (p1 >= 5) 0 else p2
						
					} else {
						if (p2 >= 5) 0 else p2 + 1
						// daca castiga 5 jocuri, castiga un set si se reseteaza numarul de jocuri castigate 
					})
	val newP1Sets =
		Apply(p1WinsGame, p1Games, p1Sets,
				(wins: Boolean, games: Int, sets: Int) =>
					if (wins && games == 5) sets + 1 else sets)
					//creste variabila sets, daca un set a fost castigat 
					val newP2Sets =
		Apply(p1WinsGame, p2Games, p2Sets,
				(wins: Boolean, games: Int, sets: Int) =>
					if (!wins && games == 5) sets + 1 else sets)
					//creste variabila sets, daca un set a fost castigat 
	val matchOver =
		Apply(newP1Sets, newP2Sets, (p1: Int, p2: Int) =>
			p1 >= 2 || p2 >= 2)
			//concursul se incheie daca player1 a castigat 2 seturi sau daca player2 a castigat 2 seturi
	If(matchOver,
		Apply(newP1Sets, (sets: Int) => sets >= 2),
		play(!p1Serves, newP1Sets, newP2Sets, newP1Games, newP2Games))
	}
	play(true, Constant(0), Constant(0), Constant(0), Constant(0))
	// se incepe jocul
 }

  def main(args: Array[String]) {
	  
	  tennis(
			probP1ServeWin: 1.0, probP1Winner: 0.5, probP1Error: 0.3,
			probP2ServeWin: 0.5, probP2Winner: 0.4, probP2Error: 0.5)
	  game(p1Serves, newP1Points, newP2Points)
  }
}
