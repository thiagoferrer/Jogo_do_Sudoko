package br.com.dio.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static br.com.dio.model.GameStatusEnum.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {

    private final List<List<Space>> spaces;

    public Board(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus() {
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))) {
            return NON_STARTED;
        }

        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors() {
        if (getStatus() == NON_STARTED) {
            return false;
        }

        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));
    }

    public boolean changeValue(final int col, final int row, final int value) {
        var space = spaces.get(col).get(row);
        if (space.isFixed()) return false;
        if (!isValidMove(col, row, value)) return false; // NOVO: valida regra do Sudoku
        space.setActual(value);
        return true;
    }


    public boolean clearValue(final int col, final int row) {
        var space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return false;
        }
        space.clearSpace();
        return true;
    }

    public void reset() {
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished() {
        return !hasErrors() && getStatus().equals(COMPLETE);
    }

    public boolean isValidMove(final int col, final int row, final int value) {
        // célula fixa nunca pode mudar
        var space = spaces.get(col).get(row);
        if (space.isFixed()) return false;

        // regra da coluna (varre linhas)
        for (int r = 0; r < 9; r++) {
            if (r == row) continue;
            var s = spaces.get(col).get(r).getActual();
            if (s != null && s == value) return false;
        }

        // regra da linha (varre colunas)
        for (int c = 0; c < 9; c++) {
            if (c == col) continue;
            var s = spaces.get(c).get(row).getActual();
            if (s != null && s == value) return false;
        }

        // regra do setor 3x3
        int startCol = (col / 3) * 3;
        int startRow = (row / 3) * 3;
        for (int c = startCol; c < startCol + 3; c++) {
            for (int r = startRow; r < startRow + 3; r++) {
                if (c == col && r == row) continue;
                var s = spaces.get(c).get(r).getActual();
                if (s != null && s == value) return false;
            }
        }
        return true;
    }

    /**
     * possíveis candidatos (1..9) que não quebram a regra local
     */
    public List<Integer> candidates(final int col, final int row) {
        List<Integer> result = new ArrayList<>();
        for (int v = 1; v <= 9; v++) {
            if (isValidMove(col, row, v)) result.add(v);
        }
        return result;
    }

}
