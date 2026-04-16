
class Triangle{
    private float Base;
    private float Height;
    Triangle(float Base,float height){
        this.Base = Base;
        this.Height = height;
    }
    Triangle(int Base ,int height){
        this.Base = Base;
        this.Height = height;
    }
    public double getArea(){
        return Base * Height * 0.5;
    }
}
public class Learn {
    public static void main(String[] args) {
        Triangle t1 = new Triangle(10.5f, 5.0f);
        Triangle t2 = new Triangle(8, 4);
        System.out.println("Area of Triangle 1: " + t1.getArea()+" metres");
        System.out.println("Area of Triangle 2: " + t2.getArea() + " metres");
    }
}