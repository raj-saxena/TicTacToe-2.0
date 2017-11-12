

class Board(size: Int) {
  type Symbol = Option[String]
  type Row = Seq[Symbol]
  type GameBoard = Seq[Row]

  val state: GameBoard = for (_ <- 0 until size) yield for (_ <- 0 until size) yield None
}
