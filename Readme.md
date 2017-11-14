# Tic Tac Toe - version 2.0

An attempt to create a Tic-tac-toe for 3 players where playfield is between 3x3 to 10x10.

* Run tests with `sbt test`

### Design choices
* The following key classes play important role
    * `Player` - contains symbol for every player.
    * `Board` - contains state of the game and operations on it.
    * `GameManager`
        - Creates new game if the board size is right.
        - Displays board.
        - Manages whose turn it is.
        - Checks for winner after every move.
        - Declares winner or if game cannot be won.
* `ScalaTest` for testing
    - `FlatSpec` for unit/integration.
    - `FeautureSpec` for acceptance.