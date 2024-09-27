package net.sixik.sdmuilibrary.client.utils.math;

public class Vector2Buffer {

    public Vector2[] vector2s;

    protected Vector2Buffer(Vector2[] vector2s){
        this.vector2s = vector2s;
    }

    public static Vector2Buffer create(Vector2... vector2s){
        return new Vector2Buffer(vector2s);
    }
}
