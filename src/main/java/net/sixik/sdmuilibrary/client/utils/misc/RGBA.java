package net.sixik.sdmuilibrary.client.utils.misc;


public class RGBA extends RGB {

    public int a;

    protected RGBA(int r, int g, int b, int a) {
        super(r, g, b);
        this.a = a;
    }

    public static RGBA fromRGB(int r, int g, int b){
        return new RGBA(r, g, b, 255);
    }

    public static RGBA fromRGB(RGB color){
        return new RGBA(color.r,color.g,color.b, 255);
    }

    public static RGBA fromARGB(int argb){
        return new RGBA((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, argb & 0xFF, (argb >> 24) & 0xFF);
    }

    public static RGBA fromHex(String hex){
        return fromARGB(Integer.parseInt(hex, 16));
    }

    public static RGBA create(int r, int g, int b, int a){
        return new RGBA(r, g, b, a);
    }

    @Override
    public int toInt() {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public RGB toRGB(){
        return new RGB(r, g, b);
    }

    @Override
    public RGBA toARGB() {
        return this;
    }

    public RGBA withAlpha(int alpha){
        a = alpha;
        return this;
    }
}
