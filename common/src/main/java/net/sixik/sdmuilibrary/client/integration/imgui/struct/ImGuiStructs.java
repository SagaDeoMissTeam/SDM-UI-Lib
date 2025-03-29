package net.sixik.sdmuilibrary.client.integration.imgui.struct;

public class ImGuiStructs {

    /**
     * @param name Имя элемента под индексом
     * @param index Индекс элемента в массиве
     */
    public record ComboBox(String name, int index) {}

}
