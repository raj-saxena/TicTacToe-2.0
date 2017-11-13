import model.Position
import org.scalatest.{FlatSpec, Matchers}

class BoardSpec extends FlatSpec with Matchers {
  val playerOne = Player("X")
  val playerTwo = Player("O")
  val playerThree = Player("C")

  "Board" should "be empty when created" in {
    val state = Board.createGame(3, playerOne, playerTwo, playerThree).state

    state should be(Vector(Vector(None, None, None), Vector(None, None, None), Vector(None, None, None)))
  }

  it should "return board state as string" in {
    val board = Board.createGame(3, playerOne, playerTwo, playerThree)

    Board.getStateStr(board) should be(
      """_ | _ | _
        |_ | _ | _
        |_ | _ | _""".stripMargin)
  }

  it should "tell next player circularly" in {
    val board = Board.createGame(3, playerOne, playerTwo, playerThree)

    board.nextPlayer should be(playerOne)
    board.nextPlayer should be(playerTwo)
    board.nextPlayer should be(playerThree)
    board.nextPlayer should be(playerOne)
  }

  it should "change state and update next player after a player makes valid move at empty location" in {
    val board = Board.createGame(3, playerOne, playerTwo, playerThree)
    val player = board.nextPlayer

    val updatedBoard = board.mark(Position(1, 0), player)

    Board.getStateStr(updatedBoard) should be(
      """_ | X | _
        |_ | _ | _
        |_ | _ | _""".stripMargin
    )

    player should be(playerOne)
    board.nextPlayer should be(playerTwo)
  }

  it should "throw exception if player makes invalid move out of Gameboard" in {
    val board = Board.createGame(3, playerOne, playerTwo, playerThree)
    val player = board.nextPlayer

    assertThrows[IllegalArgumentException] {
      board.mark(Position(-1, -1), player)
    }

    assertThrows[IllegalArgumentException] {
      board.mark(Position(4, 4), player)
    }
  }

  it should "throw exception if player makes invalid move at already occupied place" in {
    val board = Board.createGame(3, playerOne, playerTwo, playerThree)
    val player = board.nextPlayer
    val position = Position(0, 0)

    val updatedBoard = board.mark(position, player)

    val nextPlayer = board.nextPlayer
    assertThrows[IllegalArgumentException] {
      updatedBoard.mark(position, nextPlayer)
    }
  }
}
