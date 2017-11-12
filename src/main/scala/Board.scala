class Board(size: Int) {
  type Symbol = String
  type Cell = Option[Symbol]
  type Row = Seq[Cell]
  type GameBoard = Seq[Row]

  val state: GameBoard = for (_ <- 0 until size) yield for (_ <- 0 until size) yield None

  private def getHorizontalSymbols(row: Row) = for (cell <- row) yield cell

  def getStateStr: String = {
    val symbolRows = for {
      row <- state
    } yield getHorizontalSymbols(row) map {
      case Some(p) => p
      case _ => "_"
    } mkString " | "

    symbolRows mkString "\n"
  }
}

object Test {
  def main(args: Array[String]): Unit = {
    println(new Board(3).getStateStr)
  }
}