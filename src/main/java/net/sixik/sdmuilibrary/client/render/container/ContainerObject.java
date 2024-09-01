package net.sixik.sdmuilibrary.client.render.container;

public class ContainerObject<T> {
    public String name;
    public T object;

    public ContainerObject(String name, T object) {
        this.name = name;
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public String getName() {
        return name;
    }
}
