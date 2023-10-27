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
    public Lion(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public void makeSound() {
        System.out.println("The lion roars loudly.");
    }
}

// Elephant class
class Elephant extends Animal {
    public Elephant(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public void makeSound() {
        System.out.println("The elephant trumpets.");
    }
}

// Giraffe class
class Giraffe extends Animal {
    public Giraffe(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public void makeSound() {
        System.out.println("The giraffe makes a gentle humming noise.");
    }
}

// Tiger class (New animal type)
class Tiger extends Animal {
    public Tiger(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public void makeSound() {
        System.out.println("The tiger growls and snarls.");
    }

    public void roar() {
        System.out.println("The tiger roars loudly.");
    }
}

// Penguin class (New animal type)
class Penguin extends Animal {
    public Penguin(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public void makeSound() {
        System.out.println("The penguin makes a chirping sound.");
    }

    public void swim() {
        System.out.println("The penguin swims gracefully.");
    }
}

// Kangaroo class (New animal type)
class Kangaroo extends Animal {
    public Kangaroo(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public void makeSound() {
        System.out.println("The kangaroo makes a hopping noise.");
    }

    public void jump() {
        System.out.println("The kangaroo jumps high and far.");
    }
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
