// Animal interface (Abstraction)
interface Animal {
    void makeSound();
}

// SpecialBehaviors interface (Abstraction)
interface SpecialBehaviors {
    void performSpecialAction();
}

// Lion class
class Lion implements Animal {
    @Override

}

// Elephant class
class Elephant implements Animal {
    @Override

}

// Giraffe class
class Giraffe implements Animal {
    @Override

}

// Tiger class (Implements SpecialBehaviors)
class Tiger implements Animal, SpecialBehaviors {
    @Override


    @Override

}

// Penguin class (Implements SpecialBehaviors)
class Penguin implements Animal, SpecialBehaviors {
    @Override


    @Override

}

// Kangaroo class (Implements SpecialBehaviors)
class Kangaroo implements Animal, SpecialBehaviors {
    @Override


    @Override

}

public class ZooManager {
    public static void main(String[] args) {
        // Create instances of various animals
        Animal[] zooAnimals = new Animal[6];
        zooAnimals[0] = new Lion();
        zooAnimals[1] = new Elephant();
        zooAnimals[2] = new Giraffe();
        zooAnimals[3] = new Tiger();
        zooAnimals[4] = new Penguin();
        zooAnimals[5] = new Kangaroo();

        // Use polymorphism to demonstrate various actions
        for (Animal animal : zooAnimals) {
            System.out.println("Name: " + animal.getClass().getSimpleName());
            animal.makeSound();

            if (animal instanceof SpecialBehaviors) {
                ((SpecialBehaviors) animal).performSpecialAction();
            }

            System.out.println();
        }
    }
}
