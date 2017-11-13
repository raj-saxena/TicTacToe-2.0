import Board.Grid
import model.Position

class Board(val state: Grid, size: Int, playerOne: Player, playerTwo: Player, playerThree: Player) {
  private val maxIndex = size - 1

  def mark(p: Position, player: Player): Board = {
    if (p.x < 0 || p.x > maxIndex || p.y < 0 || p.y > maxIndex) throw new IllegalArgumentException(s"invalid position $p")

    val elem = state(p.y)(p.x)
    elem match {
      case None => new Board(state.updated(p.y, state(p.y).updated(p.x, Some(player))), size, nextPlayer, nextPlayer, nextPlayer)
      case _ => throw new IllegalArgumentException("Position already occupied")
    }
  }

  private val playerSequence = Iterator.continually(List(playerOne, playerTwo, playerThree)).flatten

  def nextPlayer: Player = playerSequence.next()
}

object Board {
  type Cell = Option[Player]
  type Row = Seq[Cell]
  type Grid = Seq[Row]

  def createGame(size: Int, playerOne: Player, playerTwo: Player, playerThree: Player): Board = {
    val initState = for (_ <- 0 until size) yield for (_ <- 0 until size) yield None

    new Board(initState, size, playerOne, playerTwo, playerThree)
  }

  private def getCellsBy(row: Row) = for (cell <- row) yield cell

  def getStateStr(board: Board): String = {
    val cellValuesByRow = for {
      row <- board.state
    } yield getCellsBy(row) map {
      case Some(p) => p.symbol
      case _ => "_"
    } mkString " | "

    cellValuesByRow mkString "\n"
  }
}