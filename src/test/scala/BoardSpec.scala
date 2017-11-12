import org.scalatest.{FlatSpec, Matchers}

class BoardSpec extends FlatSpec with Matchers {
  "Board" should "be empty when created" in {
    val state = new Board(3).state

    state should be (Vector(Vector(None, None, None), Vector(None, None, None), Vector(None, None, None)))
  }
}
