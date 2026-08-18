const HUMAN = "player";
const AI = "computer";
const BOARD_SIZE = 8;

const boardEl = document.querySelector("#board");
const statusEl = document.querySelector("#status");
const playerScoreEl = document.querySelector("#playerScore");
const computerScoreEl = document.querySelector("#computerScore");
const turnLabelEl = document.querySelector("#turnLabel");
const movesEl = document.querySelector("#moves");
const difficultyEl = document.querySelector("#difficulty");
const newGameEl = document.querySelector("#newGame");

let board = createInitialBoard();
let turn = HUMAN;
let selected = null;
let legalMoves = [];
let history = [];
let thinking = false;

function createInitialBoard() {
  return Array.from({ length: BOARD_SIZE }, (_, row) =>
    Array.from({ length: BOARD_SIZE }, (_, col) => {
      if ((row + col) % 2 === 0) return null;
      if (row < 3) return { side: AI, king: false };
      if (row > 4) return { side: HUMAN, king: false };
      return null;
    }),
  );
}

function cloneBoard(source) {
  return source.map((row) => row.map((piece) => (piece ? { ...piece } : null)));
}

function inBounds(row, col) {
  return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
}

function directions(piece) {
  if (piece.king) {
    return [
      [-1, -1],
      [-1, 1],
      [1, -1],
      [1, 1],
    ];
  }

  return piece.side === HUMAN
    ? [
        [-1, -1],
        [-1, 1],
      ]
    : [
        [1, -1],
        [1, 1],
      ];
}

function captureDirections() {
  return [
    [-1, -1],
    [-1, 1],
    [1, -1],
    [1, 1],
  ];
}

function getMoves(state, side) {
  const captures = [];
  const simple = [];

  for (let row = 0; row < BOARD_SIZE; row += 1) {
    for (let col = 0; col < BOARD_SIZE; col += 1) {
      const piece = state[row][col];
      if (!piece || piece.side !== side) continue;

      const pieceCaptures = getCapturesForPiece(state, row, col);
      captures.push(...pieceCaptures);

      if (pieceCaptures.length === 0) {
        for (const [dr, dc] of directions(piece)) {
          const nr = row + dr;
          const nc = col + dc;
          if (inBounds(nr, nc) && !state[nr][nc]) {
            simple.push({
              from: { row, col },
              to: { row: nr, col: nc },
              captures: [],
              path: [{ row: nr, col: nc }],
            });
          }
        }
      }
    }
  }

  return captures.length > 0 ? captures : simple;
}

function getCapturesForPiece(state, row, col) {
  const piece = state[row][col];
  if (!piece) return [];

  const results = [];

  function walk(currentState, currentRow, currentCol, captured, path) {
    let found = false;
    const currentPiece = currentState[currentRow][currentCol];

    for (const [dr, dc] of captureDirections(currentPiece)) {
      const jumpedRow = currentRow + dr;
      const jumpedCol = currentCol + dc;
      const landingRow = currentRow + dr * 2;
      const landingCol = currentCol + dc * 2;

      if (!inBounds(landingRow, landingCol) || !inBounds(jumpedRow, jumpedCol)) continue;

      const jumped = currentState[jumpedRow][jumpedCol];
      if (!jumped || jumped.side === currentPiece.side || currentState[landingRow][landingCol]) {
        continue;
      }

      found = true;
      const nextState = cloneBoard(currentState);
      nextState[landingRow][landingCol] = nextState[currentRow][currentCol];
      nextState[currentRow][currentCol] = null;
      nextState[jumpedRow][jumpedCol] = null;

      walk(
        nextState,
        landingRow,
        landingCol,
        [...captured, { row: jumpedRow, col: jumpedCol }],
        [...path, { row: landingRow, col: landingCol }],
      );
    }

    if (!found && captured.length > 0) {
      results.push({
        from: { row, col },
        to: { row: currentRow, col: currentCol },
        captures: captured,
        path,
      });
    }
  }

  walk(state, row, col, [], []);
  return results;
}

function applyMove(state, move) {
  const next = cloneBoard(state);
  const piece = next[move.from.row][move.from.col];
  next[move.from.row][move.from.col] = null;

  for (const captured of move.captures) {
    next[captured.row][captured.col] = null;
  }

  const promoted =
    (piece.side === HUMAN && move.to.row === 0) || (piece.side === AI && move.to.row === BOARD_SIZE - 1);

  next[move.to.row][move.to.col] = {
    ...piece,
    king: piece.king || promoted,
  };

  return next;
}

function render() {
  boardEl.innerHTML = "";
  legalMoves = getMoves(board, turn);

  const selectedMoves = selected
    ? legalMoves.filter((move) => move.from.row === selected.row && move.from.col === selected.col)
    : [];

  for (let row = 0; row < BOARD_SIZE; row += 1) {
    for (let col = 0; col < BOARD_SIZE; col += 1) {
      const square = document.createElement("button");
      square.type = "button";
      square.className = `square ${(row + col) % 2 === 0 ? "light" : "dark"}`;
      square.setAttribute("role", "gridcell");
      square.setAttribute("aria-label", squareLabel(row, col));
      square.dataset.row = String(row);
      square.dataset.col = String(col);

      if (selected?.row === row && selected?.col === col) {
        square.classList.add("selected");
      }

      if (selectedMoves.some((move) => move.to.row === row && move.to.col === col)) {
        square.classList.add("legal");
      }

      const piece = board[row][col];
      if (piece) {
        const pieceEl = document.createElement("span");
        pieceEl.className = `piece ${piece.side}${piece.king ? " king" : ""}`;
        square.appendChild(pieceEl);
      }

      square.addEventListener("click", () => handleSquareClick(row, col));
      boardEl.appendChild(square);
    }
  }

  const scores = countPieces(board);
  playerScoreEl.textContent = String(scores[HUMAN]);
  computerScoreEl.textContent = String(scores[AI]);
  turnLabelEl.textContent = turn === HUMAN ? "Voce" : "Computador";
  updateStatus();
  renderHistory();
}

function squareLabel(row, col) {
  const file = String.fromCharCode(65 + col);
  return `${file}${BOARD_SIZE - row}`;
}

function handleSquareClick(row, col) {
  if (thinking || turn !== HUMAN) return;

  const piece = board[row][col];
  const movesFromSelected = selected
    ? legalMoves.filter((move) => move.from.row === selected.row && move.from.col === selected.col)
    : [];
  const chosenMove = movesFromSelected.find((move) => move.to.row === row && move.to.col === col);

  if (chosenMove) {
    makeMove(chosenMove, HUMAN);
    if (!isGameOver()) {
      thinking = true;
      render();
      window.setTimeout(playComputerTurn, 350);
    }
    return;
  }

  if (piece?.side === HUMAN) {
    const hasMove = legalMoves.some((move) => move.from.row === row && move.from.col === col);
    selected = hasMove ? { row, col } : null;
    render();
  } else {
    selected = null;
    render();
  }
}

function makeMove(move, side) {
  board = applyMove(board, move);
  history.unshift(`${side === HUMAN ? "Voce" : "Computador"}: ${describeMove(move)}`);
  selected = null;
  turn = side === HUMAN ? AI : HUMAN;
  render();
}

function playComputerTurn() {
  const depth = Number(difficultyEl.value);
  const moves = getMoves(board, AI);
  if (moves.length === 0) {
    thinking = false;
    render();
    return;
  }

  const move = chooseComputerMove(board, depth);
  thinking = false;
  makeMove(move, AI);
}

function chooseComputerMove(state, depth) {
  const moves = getMoves(state, AI);
  let bestScore = -Infinity;
  let bestMoves = [];

  for (const move of moves) {
    const score = minimax(applyMove(state, move), depth - 1, -Infinity, Infinity, false);
    if (score > bestScore) {
      bestScore = score;
      bestMoves = [move];
    } else if (score === bestScore) {
      bestMoves.push(move);
    }
  }

  return bestMoves[Math.floor(Math.random() * bestMoves.length)];
}

function minimax(state, depth, alpha, beta, maximizing) {
  const side = maximizing ? AI : HUMAN;
  const moves = getMoves(state, side);

  if (depth === 0 || moves.length === 0) {
    return evaluateBoard(state, depth);
  }

  if (maximizing) {
    let value = -Infinity;
    for (const move of moves) {
      value = Math.max(value, minimax(applyMove(state, move), depth - 1, alpha, beta, false));
      alpha = Math.max(alpha, value);
      if (alpha >= beta) break;
    }
    return value;
  }

  let value = Infinity;
  for (const move of moves) {
    value = Math.min(value, minimax(applyMove(state, move), depth - 1, alpha, beta, true));
    beta = Math.min(beta, value);
    if (alpha >= beta) break;
  }
  return value;
}

function evaluateBoard(state, depth) {
  const aiMoves = getMoves(state, AI).length;
  const humanMoves = getMoves(state, HUMAN).length;

  if (aiMoves === 0) return -10000 - depth;
  if (humanMoves === 0) return 10000 + depth;

  let score = 0;
  for (let row = 0; row < BOARD_SIZE; row += 1) {
    for (let col = 0; col < BOARD_SIZE; col += 1) {
      const piece = state[row][col];
      if (!piece) continue;

      const advancement = piece.side === AI ? row : BOARD_SIZE - 1 - row;
      const center = 3.5 - Math.abs(3.5 - col);
      const value = (piece.king ? 175 : 100) + advancement * 5 + center * 3;
      score += piece.side === AI ? value : -value;
    }
  }

  return score + (aiMoves - humanMoves) * 4;
}

function countPieces(state) {
  const scores = { [HUMAN]: 0, [AI]: 0 };
  for (const row of state) {
    for (const piece of row) {
      if (piece) scores[piece.side] += 1;
    }
  }
  return scores;
}

function isGameOver() {
  return getMoves(board, HUMAN).length === 0 || getMoves(board, AI).length === 0;
}

function updateStatus() {
  const playerMoves = getMoves(board, HUMAN);
  const computerMoves = getMoves(board, AI);

  if (playerMoves.length === 0) {
    statusEl.textContent = "Computador venceu.";
  } else if (computerMoves.length === 0) {
    statusEl.textContent = "Voce venceu.";
  } else if (thinking) {
    statusEl.textContent = "Computador pensando...";
  } else if (turn === HUMAN) {
    const mustCapture = playerMoves.some((move) => move.captures.length > 0);
    statusEl.textContent = mustCapture ? "Sua vez. Captura obrigatoria." : "Sua vez.";
  } else {
    statusEl.textContent = "Vez do computador.";
  }
}

function describeMove(move) {
  const start = squareLabel(move.from.row, move.from.col);
  const path = move.path.map((step) => squareLabel(step.row, step.col));
  const separator = move.captures.length > 0 ? "x" : "-";
  return [start, ...path].join(separator);
}

function renderHistory() {
  movesEl.innerHTML = "";
  for (const item of history.slice(0, 18)) {
    const li = document.createElement("li");
    li.textContent = item;
    movesEl.appendChild(li);
  }
}

function resetGame() {
  board = createInitialBoard();
  turn = HUMAN;
  selected = null;
  legalMoves = [];
  history = [];
  thinking = false;
  render();
}

newGameEl.addEventListener("click", resetGame);
difficultyEl.addEventListener("change", () => {
  if (turn === AI && !thinking) {
    thinking = true;
    render();
    window.setTimeout(playComputerTurn, 250);
  }
});

render();
