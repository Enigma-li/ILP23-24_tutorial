// Animal class (Abstraction)
abstract class Animal {
    private String name;
    private int age;
    private String gender;

    public Animal(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public abstract void makeSound();
}

// Lion class
class Lion extends Animal {

}

// Elephant class
class Elephant extends Animal {

}

// Giraffe class
class Giraffe extends Animal {

}

// Tiger class (New animal type)
class Tiger extends Animal {

}

// Penguin class (New animal type)
class Penguin extends Animal {

}

// Kangaroo class (New animal type)
class Kangaroo extends Animal {

}

public class ZooManager {
    public static void main(String[] args) {
        // Create instances of various animals, including the new types
        Animal[] zooAnimals = new Animal[6];
        zooAnimals[0] = new Lion("Simba", 5, "Male");
        zooAnimals[1] = new Elephant("Dumbo", 10, "Male");
        zooAnimals[2] = new Giraffe("Melman", 7, "Male");
        zooAnimals[3] = new Tiger("Rajah", 6, "Male");
        zooAnimals[4] = new Penguin("Skipper", 3, "Male");
        zooAnimals[5] = new Kangaroo("Kangy", 4, "Female");

        // Use polymorphism to demonstrate various actions
        for (Animal animal : zooAnimals) {
            System.out.println("Name: " + animal.getName());
            System.out.println("Age: " + animal.getAge());
            System.out.println("Gender: " + animal.getGender());
            animal.makeSound();

            if (animal instanceof Tiger) {
                ((Tiger) animal).roar();
            } else if (animal instanceof Penguin) {
                ((Penguin) animal).swim();
            } else if (animal instanceof Kangaroo) {
                ((Kangaroo) animal).jump();
            }

            System.out.println();
        }
    }
}
