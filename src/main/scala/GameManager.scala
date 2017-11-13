object GameManager {
  def createGame(size: Int): Board = {
    if (size < 3 || size > 10) throw new IllegalArgumentException("Size should be between 3 to 10")

    Board.createGame(3, Player(""), Player(""), Player(""))
  }
}
