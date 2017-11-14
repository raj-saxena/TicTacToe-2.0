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

  val winCount = 3
  private val neighboursToCheck = winCount

  private def checkWon(symbols: Seq[Cell], player: Player) = symbols.foldLeft(List(0)) {
    case (count :: rest, Some(`player`)) => count + 1 :: rest
    case (counts, _) => 0 :: counts
  }.max >= winCount

  private def getHorizontalSymbolsToBeChecked(row: Row, current: Int) = {
    row.slice(getMinNeighbour(current), getMaxNeighbour(row.size, current))
  }

  private def getVerticalSymbolsToBeChecked(grid: Grid, current: Position) = {
    for (row <- grid.slice(getMinNeighbour(current.y), getMaxNeighbour(grid.size, current.y))) yield row(current.x)
  }

  private def getMaxNeighbour(maxSize: Int, current: Int) = {
    if (current + neighboursToCheck >= maxSize) maxSize else current + neighboursToCheck
  }

  private def getMinNeighbour(current: Int) = {
    if (current - neighboursToCheck < 0) 0 else current - neighboursToCheck
  }

  private def getTopLeftToBottomRightSymbols(grid: Grid, p: Position) = {
    val positions = (getMinNeighbour(p.x) until getMaxNeighbour(grid.size, p.x)) zip (getMinNeighbour(p.y) until getMaxNeighbour(grid.size, p.y))

    getSymbolsAt(positions, grid)
  }

  private def getSymbolsAt(positions: Seq[(Int, Int)], grid: Grid) = {
    for ((x, y) <- positions) yield grid(x)(y)
  }

  private def getBottomLeftToTopRightSymbols(grid: Grid, p: Position) = {
    val minX = getMinNeighbour(p.x)
    val maxX = getMaxNeighbour(grid.size, p.x)
    val minY = getMinNeighbour(p.y)
    val maxY = getMaxNeighbour(grid.size, p.y)

    //slope is reversed so y decreases as x increases
    val positions = (minX until maxX) zip (minY until maxY).reverse

    getSymbolsAt(positions, grid)
  }

  def getIfWinner(board: Board, p: Position, player: Player): Option[Player] = {
    // For horizontal, vertical and diagonal =>
    // Find Seq[symbols] from min backwards to max forward.
    // Min backwards to check = current - neighboursToCheck
    // Max ahead to check = current + neighboursToCheck

    val horizontalSymbols = getHorizontalSymbolsToBeChecked(board.state(p.y), p.x)
    val verticalSymbols = getVerticalSymbolsToBeChecked(board.state, p)
    val topLeftToBottomRightSymbols = getTopLeftToBottomRightSymbols(board.state, p)
    val bottomLeftToTopRightSymbols = getBottomLeftToTopRightSymbols(board.state, p)

    // Fold left and count for symbols. If maxCount > winCount player is winner else None.
    if (checkWon(horizontalSymbols, player) ||
      checkWon(verticalSymbols, player) ||
      checkWon(topLeftToBottomRightSymbols, player) ||
      checkWon(bottomLeftToTopRightSymbols, player)
    )
      Some(player)
    else
      None
  }

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