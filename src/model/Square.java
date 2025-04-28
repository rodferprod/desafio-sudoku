package model;

public class Square {
    private Integer actual;
    private final int expected;
    private final boolean fixed;

    public Square(final int expected, final boolean fixed) {
        this.expected = expected;
        this.fixed = fixed;
        if (fixed) {
            actual = expected;
        }
    }

    public void setActual(Integer actual) {
        if (isFixed())
            return;
        this.actual = actual;
    }

    public void clearSquare() {
        setActual(null);
    }

    public Integer getActual() {
        return actual;
    }

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }
}
