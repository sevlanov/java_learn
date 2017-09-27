package ru.stqa.learn.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {
    @Test
    public void testDistanceEqualsPoints() {
        Point p1 = new Point(1,1);
        Point p2 = new Point(1,1);
        Assert.assertEquals(p1.distance(p2), 0.0);
    }
    @Test
    public void testDistance() {
        Point p1 = new Point(1,2);
        Point p2 = new Point(3,4);
        Assert.assertEquals(p1.distance(p2), 2.8284271247461903);
    }
    @Test
    public void testDistanceDelta() {
        Point p1 = new Point(1,2);
        Point p2 = new Point(3,4);
        Assert.assertEquals(p1.distance(p2), 2.8, 0.1);
    }
}
