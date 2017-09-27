package ru.stqa.learn.sandbox;

public class PointDistance {

    public static void main (String[] args) {
        Point p1 = new Point(2,2);
        Point p2 = new Point(2, 4);
        System.out.println("Работа функции distance:");
        System.out.println("Расстояние между точками (" + p1.x + ", " + p1.y + ") и (" + p2.x + ", " + p2.y + ") = "
                + distance(p1, p2));
        System.out.println("Работа метода класса Point:");
        System.out.println("Расстояние между точками (" + p1.x + ", " + p1.y + ") и (" + p2.x + ", " + p2.y + ") = "
                + p1.distance(p2));
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }
}
