package ru.academits.podlatov.lambdas;

import java.util.Objects;

public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be null");
        }

        if (age < 0) {
            throw new IllegalArgumentException("Age = " + age + ", age can't be < 0.");
        }

        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be null.");
        }

        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age = " + age + ". Age can't be <=0");
        }

        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person person = (Person) o;

        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "[" + name + ", age " + age + "]";
    }
}