import org.scalatest.{FlatSpec, Matchers}

class GameManagerSpec extends FlatSpec with Matchers {

  "GameManager" should "create empty board" in {
    val size = 3

    val board = GameManager.createGame(3)

    board should not be null
  }

  it should "throw exception if boardSize is less than 3 or greater than 10" in {
    assertThrows[IllegalArgumentException] {
      GameManager.createGame(2)
    }

    assertThrows[IllegalArgumentException] {
      GameManager.createGame(11)
    }
  }
}
