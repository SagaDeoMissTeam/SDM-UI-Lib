package net.sixik.sdmuilibrary.client.integration.imgui.extern;

import imgui.ImGui;
import imgui.type.*;
import net.minecraft.nbt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TagEditor<T extends Tag> {
    private static final int BUFFER_SIZE = 512;

    private final String name;
    private final T tag;
    private final Consumer<T> consumer;
    private boolean valueChanged = false;
    private ListTag currentListTag = null;

    public TagEditor(String name, T tag, Consumer<T> consumer) {
        this.name = name;
        this.tag = tag;
        this.consumer = consumer;
    }

    public boolean edit() {
        return switch (tag.getId()) {
            case 1 -> editByteTag();
            case 2 -> editShortTag();
            case 3 -> editIntTag();
            case 4 -> editLongTag();
            case 5 -> editFloatTag();
            case 6 -> editDoubleTag();
            case 7 -> editByteArrayTag();
            case 8 -> editStringTag();
            case 9 -> editListTag();
            case 10 -> editCompoundTag();
            case 11 -> editIntArrayTag();
            case 12 -> editLongArrayTag();
            default -> false;
        };
    }

    private boolean editByteTag() {
        ByteTag byteTag = (ByteTag) tag;
        ImInt value = new ImInt(byteTag.getAsByte());

        if (handleIntInput("Byte", value)) {
            int clampedValue = Math.min(Math.max(value.get(), Byte.MIN_VALUE), Byte.MAX_VALUE);
            consumer.accept((T) ByteTag.valueOf((byte) clampedValue));
            return true;
        }
        return false;
    }

    private boolean editShortTag() {
        ShortTag shortTag = (ShortTag) tag;
        ImInt value = new ImInt(shortTag.getAsShort());

        if (handleIntInput("Short", value)) {
            int clampedValue = Math.min(Math.max(value.get(), Short.MIN_VALUE), Short.MAX_VALUE);
            consumer.accept((T) ShortTag.valueOf((short) clampedValue));
            return true;
        }
        return false;
    }

    private boolean editIntTag() {
        IntTag intTag = (IntTag) tag;
        ImInt value = new ImInt(intTag.getAsInt());

        if (handleIntInput("Int", value)) {
            consumer.accept((T) IntTag.valueOf(value.get()));
            return true;
        }
        return false;
    }

    private boolean editLongTag() {
        LongTag longTag = (LongTag) tag;
        ImLong value = new ImLong(longTag.getAsLong());

        if (ImGui.inputScalar(name, value)) {
            consumer.accept((T) LongTag.valueOf(value.get()));
            return true;
        }
        setTooltipIfHovered("Long");
        return false;
    }

    private boolean editFloatTag() {
        FloatTag floatTag = (FloatTag) tag;
        ImFloat value = new ImFloat(floatTag.getAsFloat());

        if (ImGui.inputFloat(name, value)) {
            consumer.accept((T) FloatTag.valueOf(value.get()));
            return true;
        }
        setTooltipIfHovered("Float");
        return false;
    }

    private boolean editDoubleTag() {
        DoubleTag doubleTag = (DoubleTag) tag;
        ImDouble value = new ImDouble(doubleTag.getAsDouble());

        if (ImGui.inputDouble(name, value)) {
            consumer.accept((T) DoubleTag.valueOf(value.get()));
            return true;
        }
        setTooltipIfHovered("Double");
        return false;
    }

    private boolean editStringTag() {
        StringTag stringTag = (StringTag) tag;
        ImString value = new ImString(stringTag.getAsString(), BUFFER_SIZE);

        if (ImGui.inputText(name, value)) {
            consumer.accept((T) StringTag.valueOf(value.get()));
            return true;
        }
        setTooltipIfHovered("String");
        return false;
    }

    private boolean editByteArrayTag() {
        return editArrayTag((ByteArrayTag) tag, "Byte Array");
    }

    private boolean editIntArrayTag() {
        return editArrayTag((IntArrayTag) tag, "Int Array");
    }

    private boolean editLongArrayTag() {
        return editArrayTag((LongArrayTag) tag, "Long Array");
    }

    private boolean editListTag() {
        currentListTag = (ListTag) tag;
        boolean result = editCollectionTag(currentListTag, "List");
        currentListTag = null;
        return result;
    }

    private boolean editCompoundTag() {
        CompoundTag compoundTag = (CompoundTag) tag;
        ImGui.pushID(name + "_compoundTag");

        boolean value = ImGui.treeNode(name);
        setTooltipIfHovered("CompoundTag");
        if (value) {
            for (String key : compoundTag.getAllKeys()) {
                if (BTSImGui.beginEditElementTag(key, compoundTag.get(key), s -> {
                    compoundTag.put(key, s);
                    consumer.accept((T) compoundTag);
                })) {
                    valueChanged = true;
                }
            }
            ImGui.treePop();
        }
        ImGui.popID();

        return valueChanged;
    }

    private <S extends Tag> boolean editArrayTag(CollectionTag<S> arrayTag, String tooltip) {
        ImGui.pushID(name + "_" + tooltip.replace(" ", ""));
        List<Integer> indicesToRemove = new ArrayList<>();

        boolean value = ImGui.treeNode(name);
        setTooltipIfHovered(tooltip);

        if (value) {
            for (int i = 0; i < arrayTag.size(); i++) {
                int finalI = i;
                if (BTSImGui.beginEditElementTag(String.valueOf(i), arrayTag.get(i), s -> {
                    arrayTag.set(finalI, s);
                    consumer.accept((T) arrayTag);
                })) {
                    valueChanged = true;
                }

                handleItemContextMenu(arrayTag, i, indicesToRemove);
            }

            ImGui.treePop();
        }

        ImGui.popID();

        for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
            arrayTag.remove(indicesToRemove.get(i).intValue());
            valueChanged = true;
        }

        if (valueChanged) {
            consumer.accept((T) arrayTag);
        }

        return valueChanged;
    }

    private <S extends Tag> boolean editCollectionTag(CollectionTag<S> collectionTag, String tooltip) {
        ImGui.pushID(name + "_" + tooltip.replace(" ", ""));
        List<Integer> indicesToRemove = new ArrayList<>();

        boolean value = ImGui.treeNode(name);
        setTooltipIfHovered(tooltip);

        if (value) {
            for (int i = 0; i < collectionTag.size(); i++) {
                int finalI = i;
                if (BTSImGui.beginEditElementTag(String.valueOf(i), collectionTag.get(i), s -> {
                    collectionTag.set(finalI, s);
                    consumer.accept((T) collectionTag);
                })) {
                    valueChanged = true;
                }

                handleItemContextMenu(collectionTag, i, indicesToRemove);
            }

            ImGui.treePop();
        }

        ImGui.popID();

        for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
            collectionTag.remove(indicesToRemove.get(i).intValue());
            valueChanged = true;
        }

        if (valueChanged) {
            consumer.accept((T) collectionTag);
        }

        return valueChanged;
    }

    private <S extends Tag> void handleItemContextMenu(CollectionTag<S> collectionTag, int index, List<Integer> indicesToRemove) {
        if (ImGui.beginPopupContextItem("context_" + index + "_" + name)) {
            if (ImGui.beginMenu("Добавить")) {
                if (collectionTag instanceof ByteArrayTag) {
                    if (ImGui.menuItem("Byte")) {
                        collectionTag.add((S) ByteTag.valueOf((byte) 0));
                        valueChanged = true;
                    }
                }
                else if (collectionTag instanceof IntArrayTag) {
                    if (ImGui.menuItem("Int")) {
                        collectionTag.add((S) IntTag.valueOf(0));
                        valueChanged = true;
                    }
                }
                else if (collectionTag instanceof LongArrayTag) {
                    if (ImGui.menuItem("Long")) {
                        collectionTag.add((S) LongTag.valueOf(0L));
                        valueChanged = true;
                    }
                }
                else if (collectionTag instanceof ListTag listTag) {
                    if(listTag.getElementType() == 0) {
                        if (ImGui.menuItem("Byte")) {
                            addElementToCollection(collectionTag, ByteTag.valueOf((byte) 0));
                        }
                        if (ImGui.menuItem("Short")) {
                            addElementToCollection(collectionTag, ShortTag.valueOf((short) 0));
                        }
                        if (ImGui.menuItem("Int")) {
                            addElementToCollection(collectionTag, IntTag.valueOf(0));
                        }
                        if (ImGui.menuItem("Long")) {
                            addElementToCollection(collectionTag, LongTag.valueOf(0));
                        }
                        if (ImGui.menuItem("Float")) {
                            addElementToCollection(collectionTag, FloatTag.valueOf(0));
                        }
                        if (ImGui.menuItem("Double")) {
                            addElementToCollection(collectionTag, DoubleTag.valueOf(0));
                        }
                        if (ImGui.menuItem("String")) {
                            addElementToCollection(collectionTag, StringTag.valueOf(""));
                        }
                        if (ImGui.menuItem("CompoundTag")) {
                            addElementToCollection(collectionTag, new CompoundTag());
                        }
                        if (ImGui.menuItem("List")) {
                            addElementToCollection(collectionTag, new ListTag());
                        }
                        if (ImGui.menuItem("Byte Array")) {
                            addElementToCollection(collectionTag, new ByteArrayTag(new ArrayList<>()));
                        }
                        if (ImGui.menuItem("Int Array")) {
                            addElementToCollection(collectionTag, new IntArrayTag(new ArrayList<>()));
                        }
                        if (ImGui.menuItem("Long Array")) {
                            addElementToCollection(collectionTag, new LongArrayTag(new ArrayList<>()));
                        }
                    } else {
                        switch (listTag.getElementType()) {
                            case 1 -> {
                                if (ImGui.menuItem("Byte")) {
                                    addElementToCollection(collectionTag, ByteTag.valueOf((byte) 0));
                                }
                            }
                            case 2 -> {
                                if (ImGui.menuItem("Short")) {
                                    addElementToCollection(collectionTag, ShortTag.valueOf((short) 0));
                                }
                            }
                            case 3 -> {
                                if (ImGui.menuItem("Int")) {
                                    addElementToCollection(collectionTag, IntTag.valueOf(0));
                                }
                            }
                            case 4 -> {
                                if (ImGui.menuItem("Long")) {
                                    addElementToCollection(collectionTag, LongTag.valueOf(0));
                                }
                            }
                            case 5 -> {
                                if (ImGui.menuItem("Float")) {
                                    addElementToCollection(collectionTag, FloatTag.valueOf(0));
                                }
                            }
                            case 6 -> {
                                if (ImGui.menuItem("Double")) {
                                    addElementToCollection(collectionTag, DoubleTag.valueOf(0));
                                }
                            }
                            case 7 -> {
                                if (ImGui.menuItem("Byte Array")) {
                                    addElementToCollection(collectionTag, new ByteArrayTag(new ArrayList<>()));
                                }
                            }
                            case 8 -> {
                                if (ImGui.menuItem("String")) {
                                    addElementToCollection(collectionTag, StringTag.valueOf(""));
                                }
                            }
                            case 9 -> {
                                if (ImGui.menuItem("List")) {
                                    addElementToCollection(collectionTag, new ListTag());
                                }
                            }
                            case 10 -> {
                                if (ImGui.menuItem("CompoundTag")) {
                                    addElementToCollection(collectionTag, new CompoundTag());
                                }
                            }
                            case 11 -> {
                                if (ImGui.menuItem("Int Array")) {
                                    addElementToCollection(collectionTag, new IntArrayTag(new ArrayList<>()));
                                }
                            }
                            case 12 -> {
                                if (ImGui.menuItem("Long Array")) {
                                    addElementToCollection(collectionTag, new LongArrayTag(new ArrayList<>()));
                                }
                            }
                            default -> {}
                        }
                    }
                }
                ImGui.endMenu();
            }

            if (ImGui.menuItem("Удалить")) {
                indicesToRemove.add(index);
            }

            ImGui.endPopup();
        }
    }

    private <S extends Tag> void addElementToCollection(CollectionTag<S> collectionTag, Tag newTag) {
        if (collectionTag instanceof ListTag) {
            collectionTag.add((S) newTag);
            valueChanged = true;
            consumer.accept((T) collectionTag);
        }
    }

    private boolean handleIntInput(String tooltip, ImInt value) {
        if (ImGui.inputInt(name, value)) {
            return true;
        }
        setTooltipIfHovered(tooltip);
        return false;
    }

    private void setTooltipIfHovered(String tooltip) {
        if (ImGui.isItemHovered()) {
            ImGui.setTooltip(tooltip);
        }
    }
}
