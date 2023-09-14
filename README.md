# 2023-DEV3-093-TicTacToe

A plain ol' simple tic-tac-toe game as specified here: https://stephane-genicot.github.io/TicTacToe.html

To compile:

run
```shell
./mvnw clean package
```
or

```shell
mvnw.bat clean package
```

then run the artifact:

```shell
java -jar target/TickTacToe-0.0.1-SNAPSHOT.jar
```

## Endpoints
The following REST endpoints are published:

### /play
**Type:** POST

**Description:** Makes a move on the current game. It responds with the result of the move if it was accepted or rejected etc.

**Body:**

| Property | Value  |
|----------|--------|
| player   | X or Y |
| x        | 0-2    |
| y        | 0-2    |

**Responses:**

If the move was accepted and the game is still on:

| Property   | Value   |
|------------|---------|
| moveResult | SUCCESS |

If the move was rejected:

| Property   | Value   |
|------------|---------|
| moveResult | ERROR   |
| errors     | [1 - N] |

If the game ended:

| Property   | Value          |
|------------|----------------|
| moveResult | END            |
| gameResult | DRAW or X or Y |

### /status
**Type:** GET

**Description:** Gets a text representation of the board
Parameters
separator: text to use that will separate the rows. newline will be replaced with the system's newline, pipe will be replaced with the pipe character.

_(granted, not the best API, but I wanted to add a @RequestParam and I was running out of ideas)_

### /reset
**Type:** PUT

**Description:** Resets the board
