package j8;

/**
 * Created by nokdu on 2017-04-04.
 */
public class Apple {
    private String color;

    public Apple(String color) {
        this.color = color;
    }

    public Apple() {

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public enum COLOR{
        RED(0),
        YELLOW(1),
        GREEN(2),
        BLUE(3);
        private int color;
        COLOR(int i) {
            color=i;
        }
    };
}
