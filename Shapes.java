class Shape {
    private String color;

    public Shape(String color) {
        this.color = color;
    }

    public double getArea() {
        return 0;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Shape{ color='" + color + "', area=" + getArea() + "}";
    }
}
class Circle extends Shape {
    private double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public String toString() {
        return "Circle = [ color='" + getColor() + "', radius=" + radius + ", area=" + getArea() + " ]";
    }
}
class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public String toString() {
        return "Rectangle= [ color='" + getColor() + "', width=" + width
                + ", height=" + height + ", area=" + getArea() + " ]";
    }

}
public class Shapes {
    public static void main(String[] args) {
        Shape circle = new Circle("Red", 7.0);
        Shape rectangle = new Rectangle("Blue", 4.0, 6.0);

        System.out.println(circle);
        System.out.println(rectangle);
    }
}
