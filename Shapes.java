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









}

}
