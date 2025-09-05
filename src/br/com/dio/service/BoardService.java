package br.com.dio.service;

import br.com.dio.model.Board;
import br.com.dio.model.GameStatusEnum;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardService {

    private final static int BOARD_LIMIT = 9;

    private final Board board;



    public BoardService(final Map<String, String> gameConfig) {
        this.board = new Board(initBoard(gameConfig));
    }

    public List<List<Space>> getSpace() {
        return this.board.getSpaces();
    }

    public void reset() {
        this.board.reset();
    }

    public boolean hasErros() {
        return board.hasErrors();
    }

    public GameStatusEnum getStatus() {
        return board.getStatus();
    }

    public boolean gameIsFinished() {
        return board.gameIsFinished();
    }

    private List<List<Space>> initBoard(final Map<String, String> gameConfig) {
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = gameConfig.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }
        return spaces;
    }

    public boolean isValidMove(final int col, final int row, final int value) {
        return board.isValidMove(col, row, value);
    }

    public java.util.List<Integer> candidates(final int col, final int row) {
        return board.candidates(col, row);
    }

    /**
     * opcionais, úteis para UI/console não tocarem no Board diretamente
     */
    public boolean changeValue(final int col, final int row, final int value) {
        return board.changeValue(col, row, value);
    }

    public boolean clearValue(final int col, final int row) {
        return board.clearValue(col, row);
    }

    /**
     * contadores para mensagens mais ricas
     */
    public long countEmpty() {
        return board.getSpaces().stream()
                .flatMap(java.util.Collection::stream)
                .filter(s -> s.getActual() == null)
                .count();
    }

    public long countWrong() {
        return board.getSpaces().stream()
                .flatMap(java.util.Collection::stream)
                .filter(s -> s.getActual() != null && !s.getActual().equals(s.getExpected()))
                .count();
    }
}
