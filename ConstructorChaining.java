class Animal {
    public Animal() {
        System.out.println("Animal constructor");
    }
}

class Dog extends Animal {
    public Dog() {
         // Automatically inserted by Java if omitted
        System.out.println("Dog constructor");
    }
}

class Puppy extends Dog {
    public Puppy() {
        super();
        System.out.println("Puppy constructor");
    }
}

public class ConstructorChaining {
    public static void main(String[] args) {
        Puppy p = new Puppy();
    }

}
