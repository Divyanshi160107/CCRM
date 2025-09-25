package edu.ccrm.domain;

public enum Grade {
    S(10.0),
    A(9.0),
    B(8.0),
    C(7.0),
    D(6.0),
    F(0.0);

    // Field for the grade point
    private final double points;

    // Constructor for the enum
    Grade(double points) {
        this.points = points;
    }

    // Getter for the points
    public double getPoints() {
        return points;
    }
}