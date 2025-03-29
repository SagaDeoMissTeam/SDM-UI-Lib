package net.sixik.sdmuilibrary.client.integration.imgui.extern;

import imgui.ImGui;
import imgui.callback.ImGuiInputTextCallback;
import imgui.type.*;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.joml.*;

import java.util.function.Consumer;

public class BTSImGui {

    public static void beginEditElementBoolean(String name, boolean value, Consumer<Boolean> consumer) {
        ImBoolean imInt = new ImBoolean(value);
        if(ImGui.checkbox(name, imInt)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementInt(String name, int value, Consumer<Integer> consumer) {
        ImInt imInt = new ImInt(value);
        if(ImGui.inputInt(name, imInt)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementInt(String name, int value, Consumer<Integer> consumer, final int step) {
        ImInt imInt = new ImInt(value);
        if(ImGui.inputInt(name, imInt, step)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementInt(String name, int value, Consumer<Integer> consumer, final int step, final int stepFast) {
        ImInt imInt = new ImInt(value);
        if(ImGui.inputInt(name, imInt, step, stepFast)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementInt(String name, int value, Consumer<Integer> consumer, final int step, final int stepFast, final int imGuiInputTextFlags) {
        ImInt imInt = new ImInt(value);
        if(ImGui.inputInt(name, imInt, step, stepFast, imGuiInputTextFlags)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementLong(String name, long value, Consumer<Long> consumer) {
        ImLong imInt = new ImLong(value);
        if(ImGui.inputScalar(name, imInt)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementLong(String name, long value, Consumer<Long> consumer, final long step) {
        ImLong imInt = new ImLong(value);
        if(ImGui.inputScalar(name, imInt, step)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementLong(String name, long value, Consumer<Long> consumer, final long step, final long stepFast) {
        ImLong imInt = new ImLong(value);
        if(ImGui.inputScalar(name, imInt, step, stepFast)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementLong(String name, String value, Consumer<String> consumer) {
        ImString imInt = new ImString(value);
        if(ImGui.inputText(name, imInt)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementLong(String name, String value, Consumer<String> consumer, final int imGuiInputTextFlags) {
        ImString imInt = new ImString(value);
        if(ImGui.inputText(name, imInt, imGuiInputTextFlags)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementLong(String name, String value, Consumer<String> consumer, final int imGuiInputTextFlags, final ImGuiInputTextCallback callback) {
        ImString imInt = new ImString(value);
        if(ImGui.inputText(name, imInt, imGuiInputTextFlags, callback)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementDouble(String name, double value, Consumer<Double> consumer) {
        ImDouble imInt = new ImDouble(value);
        if(ImGui.inputDouble(name, imInt)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementDouble(String name, double value, Consumer<Double> consumer, final double step) {
        ImDouble imInt = new ImDouble(value);
        if(ImGui.inputDouble(name, imInt, step)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementDouble(String name, double value, Consumer<Double> consumer, final double step, final double stepFast) {
        ImDouble imInt = new ImDouble(value);
        if(ImGui.inputDouble(name, imInt, step, stepFast)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementDouble(String name, double value, Consumer<Double> consumer, final double step, final double stepFast, final int imGuiInputTextFlags) {
        ImDouble imInt = new ImDouble(value);
        if(ImGui.inputDouble(name, imInt, step, stepFast, imGuiInputTextFlags)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementFloat(String name, float value, Consumer<Float> consumer) {
        ImFloat imInt = new ImFloat(value);
        if(ImGui.inputFloat(name, imInt)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementFloat(String name, float value, Consumer<Float> consumer, final float step) {
        ImFloat imInt = new ImFloat(value);
        if(ImGui.inputFloat(name, imInt, step)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementFloat(String name, float value, Consumer<Float> consumer, final float step, final float stepFast) {
        ImFloat imInt = new ImFloat(value);
        if(ImGui.inputFloat(name, imInt, step, stepFast)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementFloat(String name, float value, Consumer<Float> consumer, final float step, final float stepFast, final int imGuiInputTextFlags) {
        ImFloat imInt = new ImFloat(value);
        if(ImGui.inputFloat(name, imInt, step, stepFast, imGuiInputTextFlags)) {
            consumer.accept(imInt.get());
        }
    }

    public static void beginEditElementVec3i(String name, Vec3i pos, Consumer<Vec3i> consumer) {
        int[] values = new int[] { pos.getX(), pos.getY(),  pos.getZ() };
        if(ImGui.inputInt3(name, values)) {
            consumer.accept(new Vec3i(values[0], values[1], values[2]));
        }
    }

    public static void beginEditElementVector3f(String name, Vector3f pos, Consumer<Vector3f> consumer) {
        float[] values = new float[] { pos.x, pos.y,  pos.z };
        if(ImGui.inputFloat3(name, values)) {
            consumer.accept(new Vector3f(values[0], values[1], values[2]));
        }
    }

    public static void beginEditElementVector3f(String name, Vector3d pos, Consumer<Vector3d> consumer) {
        float[] values = new float[] {(float) pos.x, (float) pos.y, (float) pos.z};
        if(ImGui.inputFloat3(name, values)) {
            consumer.accept(new Vector3d(values[0], values[1], values[2]));
        }
    }

    public static void beginEditElementVector3i(String name, Vector3i pos, Consumer<Vector3i> consumer) {
        int[] values = new int[] { pos.x, pos.y,  pos.z };
        if(ImGui.inputInt3(name, values)) {
            consumer.accept(new Vector3i(values[0], values[1], values[2]));
        }
    }

    public static void beginEditElementMatrix4f(String name, Matrix4f matrix, Consumer<Matrix4f> consumer) {
        float[] values1 = new float[] { matrix.m00(), matrix.m01(), matrix.m02(), matrix.m03()};
        float[] values2 = new float[] { matrix.m10(), matrix.m11(), matrix.m12(), matrix.m13()};
        float[] values3 = new float[] { matrix.m20(), matrix.m21(), matrix.m22(), matrix.m23()};
        float[] values4 = new float[] { matrix.m30(), matrix.m31(), matrix.m32(), matrix.m33()};

        ImGui.pushID(name);
        if(ImGui.inputFloat4(name + "_Matrix_m00", values1)) {
            matrix.m00(values1[0]);
            matrix.m01(values1[1]);
            matrix.m02(values1[2]);
            matrix.m03(values1[3]);
            consumer.accept(matrix);
        }
        if(ImGui.inputFloat4(name + "_Matrix_m10", values2)) {
            matrix.m10(values2[0]);
            matrix.m11(values2[1]);
            matrix.m12(values2[2]);
            matrix.m13(values2[3]);
            consumer.accept(matrix);
        }
        if(ImGui.inputFloat4(name + "_Matrix_m20", values3)) {
            matrix.m20(values3[0]);
            matrix.m21(values3[1]);
            matrix.m22(values3[2]);
            matrix.m23(values3[3]);
            consumer.accept(matrix);
        }
        if(ImGui.inputFloat4(name + "_Matrix_m30", values4)) {
            matrix.m30(values4[0]);
            matrix.m31(values4[1]);
            matrix.m32(values4[2]);
            matrix.m33(values4[3]);
            consumer.accept(matrix);
        }
        ImGui.popID();
    }

    public static void beginEditElementMatrix3f(String name, Matrix3f matrix, Consumer<Matrix3f> consumer) {
        float[] values1 = new float[] { matrix.m00(), matrix.m01(), matrix.m02()};
        float[] values2 = new float[] { matrix.m10(), matrix.m11(), matrix.m12()};
        float[] values3 = new float[] { matrix.m20(), matrix.m21(), matrix.m22()};

        ImGui.pushID(name);
        if(ImGui.inputFloat4(name + "_Matrix_m00", values1)) {
            matrix.m00(values1[0]);
            matrix.m01(values1[1]);
            matrix.m02(values1[2]);
            consumer.accept(matrix);
        }
        if(ImGui.inputFloat4(name + "_Matrix_m10", values2)) {
            matrix.m10(values2[0]);
            matrix.m11(values2[1]);
            matrix.m12(values2[2]);
            consumer.accept(matrix);
        }
        if(ImGui.inputFloat4(name + "_Matrix_m20", values3)) {
            matrix.m20(values3[0]);
            matrix.m21(values3[1]);
            matrix.m22(values3[2]);
            consumer.accept(matrix);
        }
        ImGui.popID();
    }

    public static void beginEditElementMatrix2f(String name, Matrix2f matrix, Consumer<Matrix2f> consumer) {
        float[] values1 = new float[] { matrix.m00(), matrix.m01()};
        float[] values2 = new float[] { matrix.m10(), matrix.m11()};

        ImGui.pushID(name);
        if(ImGui.inputFloat4(name + "_Matrix_m00", values1)) {
            matrix.m00(values1[0]);
            matrix.m01(values1[1]);
            consumer.accept(matrix);
        }
        if(ImGui.inputFloat4(name + "_Matrix_m10", values2)) {
            matrix.m10(values2[0]);
            matrix.m11(values2[1]);
            consumer.accept(matrix);
        }
        ImGui.popID();
    }

    public static void beginEditElementQuaternionf(String name, Quaternionf matrix, Consumer<Quaternionf> consumer) {
        float[] values1 = new float[] { matrix.x, matrix.y, matrix.z, matrix.w};

        ImGui.pushID(name);
        if(ImGui.inputFloat4(name, values1)) {
            consumer.accept(new Quaternionf(values1[0], values1[1], values1[2], values1[3]));
        }
        ImGui.popID();
    }

    public static void beginEditElementQuaterniond(String name, Quaterniond matrix, Consumer<Quaterniond> consumer) {
        float[] values1 = new float[] {(float) matrix.x, (float) matrix.y, (float) matrix.z, (float) matrix.w};

        ImGui.pushID(name);
        if(ImGui.inputFloat4(name, values1)) {
            consumer.accept(new Quaterniond(values1[0], values1[1], values1[2], values1[3]));
        }
        ImGui.popID();
    }

    public static void beginEditElementItemStack(String name, ItemStack itemStack, Consumer<ItemStack> consumer) {
        
    }

    public static <T extends Tag> boolean beginEditElementTag(String name, T tag, Consumer<T> consumer) {
        return new TagEditor<>(name, tag, consumer).edit();
    }
}
