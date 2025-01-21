package net.sixik.sdmuilibrary.client.utils.misc;

public class RGBA extends RGB{

    public static final RGBA DEFAULT = new RGBA(255,255,255,255);

    public int a;


    public RGBA(int r, int g, int b, int a) {
        super(r, g, b);
        this.a = a;
    }

    @Override
    public RGB copy() {
        return new RGBA(this.r, this.g, this.b, this.a);
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

    @Override
    public RGBA withAlpha(int alpha) {
        this.a = alpha;
        return this;
    }

    public static RGBA interpolate(RGBA colorA, RGBA colorC, float t) {
        int r = (int) ((1 - t) * colorA.r + t * colorC.r);
        int g = (int) ((1 - t) * colorA.g + t * colorC.g);
        int b = (int) ((1 - t) * colorA.b + t * colorC.b);
        int a = (int) ((1 - t) * colorA.a + t * colorC.a);

        return new RGBA(r, g, b, a);
    }
}
