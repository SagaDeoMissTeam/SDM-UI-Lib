package net.sixik.sdmuilibrary.client.utils.math;

public class Vector2f {
    public float x;
    public float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f add(Vector2f other) {
        return new Vector2f(x + other.x, y + other.y);
    }

    public Vector2f subtract(Vector2f other) {
        return new Vector2f(x - other.x, y - other.y);
    }

    public Vector2f multiply(float scalar) {
        return new Vector2f(x * scalar, y * scalar);
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public Vector2f normalize() {
        float length = length();
        if (length == 0) {
            return new Vector2f(0, 0);
        }
        return multiply(1 / length);
    }

    public float dot(Vector2f other) {
        return x * other.x + y * other.y;
    }

    public Vector2f cross(Vector2f other) {
        return new Vector2f(y * other.x - x * other.y, x * other.y - y * other.x);
    }

    public static Vector2f zero() {
        return new Vector2f(0, 0);
    }

    public static Vector2f one() {
        return new Vector2f(1, 1);
    }

    public static Vector2f up() {
        return new Vector2f(0, 1);
    }

    public static Vector2f down() {
        return new Vector2f(0, -1);
    }

    public static Vector2f left() {
        return new Vector2f(-1, 0);
    }

    public static Vector2f right() {
        return new Vector2f(1, 0);
    }

    public static float distance(Vector2f a, Vector2f b) {
        return a.subtract(b).length();
    }

    public static float distanceSquared(Vector2f a, Vector2f b) {
        return a.subtract(b).lengthSquared();
    }

    public static Vector2f lerp(Vector2f a, Vector2f b, float t) {
        return a.add(b.subtract(a).multiply(t));
    }

    public static Vector2f project(Vector2f a, Vector2f b) {
        float dotProduct = a.dot(b);
        float lengthSquared = b.lengthSquared();
        return b.multiply(dotProduct / lengthSquared);
    }

    public static Vector2f reject(Vector2f a, Vector2f b) {
        return a.subtract(project(a, b));
    }

    public static Vector2f reflect(Vector2f incident, Vector2f normal) {
        return incident.subtract(normal.multiply(2 * incident.dot(normal)));
    }

    public static boolean equals(Vector2f a, Vector2f b) {
        return a.x == b.x && a.y == b.y;
    }

}
