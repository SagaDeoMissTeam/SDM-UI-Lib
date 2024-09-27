package net.sixik.sdmuilibrary.client.utils.misc;


/**
 * Represents an RGBA color, which includes red, green, blue, and alpha (transparency) components.
 * This class extends the RGB class and adds an alpha component.
 */
public class RGBA extends RGB {

    /**
     * The alpha component of the color.
     */
    public int a;

    /**
     * Constructs a new RGBA color with the given red, green, blue, and alpha components.
     *
     * @param r The red component (0-255).
     * @param g The green component (0-255).
     * @param b The blue component (0-255).
     * @param a The alpha component (0-255).
     */
    protected RGBA(int r, int g, int b, int a) {
        super(r, g, b);
        this.a = a;
    }

    /**
     * Creates a new RGBA color from the given red, green, and blue components, with an alpha of 255.
     *
     * @param r The red component (0-255).
     * @param g The green component (0-255).
     * @param b The blue component (0-255).
     * @return A new RGBA color.
     */
    public static RGBA fromRGB(int r, int g, int b){
        return new RGBA(r, g, b, 255);
    }

    /**
     * Creates a new RGBA color from the given RGB color, with an alpha of 255.
     *
     * @param color The RGB color.
     * @return A new RGBA color.
     */
    public static RGBA fromRGB(RGB color){
        return new RGBA(color.r,color.g,color.b, 255);
    }

    /**
     * Creates a new RGBA color from the given ARGB integer.
     *
     * @param argb The ARGB integer.
     * @return A new RGBA color.
     */
    public static RGBA fromARGB(int argb){
        return new RGBA((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, argb & 0xFF, (argb >> 24) & 0xFF);
    }

    /**
     * Creates a new RGBA color from the given hexadecimal string.
     *
     * @param hex The hexadecimal string (e.g., "#RRGGBBAA").
     * @return A new RGBA color.
     */
    public static RGBA fromHex(String hex){
        return fromARGB(Integer.parseInt(hex, 16));
    }

    /**
     * Creates a new RGBA color with the given red, green, blue, and alpha components.
     *
     * @param r The red component (0-255).
     * @param g The green component (0-255).
     * @param b The blue component (0-255).
     * @param a The alpha component (0-255).
     * @return A new RGBA color.
     */
    public static RGBA create(int r, int g, int b, int a){
        return new RGBA(r, g, b, a);
    }

    /**
     * Converts the RGBA color to an ARGB integer.
     *
     * @return The ARGB integer.
     */
    @Override
    public int toInt() {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Converts the RGBA color to an RGB color by discarding the alpha component.
     *
     * @return The RGB color.
     */
    public RGB toRGB(){
        return new RGB(r, g, b);
    }

    /**
     * Returns the RGBA color itself, as it is already an RGBA color.
     *
     * @return The RGBA color.
     */
    @Override
    public RGBA toARGB() {
        return this;
    }

    /**
     * Updates the alpha component of the RGBA color.
     *
     * @param alpha The new alpha component (0-255).
     * @return The updated RGBA color.
     */
    public RGBA withAlpha(int alpha){
        a = alpha;
        return this;
    }

    /**
     * Interpolates between two RGBA colors based on the given interpolation factor.
     *
     * @param colorA The first RGBA color to interpolate from.
     * @param colorC The second RGBA color to interpolate to.
     * @param t The interpolation factor, ranging from 0.0 to 1.0. A value of 0.0 returns {@code colorA},
     *          while a value of 1.0 returns {@code colorC}. Values in between interpolate between the two colors.
     * @return A new RGBA color representing the interpolated result.
     */
    public static RGBA interpolate(RGBA colorA, RGBA colorC, float t) {
        int r = (int) ((1 - t) * colorA.r + t * colorC.r);
        int g = (int) ((1 - t) * colorA.g + t * colorC.g);
        int b = (int) ((1 - t) * colorA.b + t * colorC.b);
        int a = (int) ((1 - t) * colorA.a + t * colorC.a);

        return new RGBA(r, g, b, a);
    }
}
