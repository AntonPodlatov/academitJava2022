package ru.academits.podlatov.person.main;

import ru.academits.podlatov.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Леонид", 12));
        people.add(new Person("Николай", 23));
        people.add(new Person("Константин", 1));
        people.add(new Person("Борис", 30));
        people.add(new Person("Анна", 17));
        people.add(new Person("Николай", 45));
        people.add(new Person("Дмитрий", 65));
        people.add(new Person("Иван", 19));
        people.add(new Person("Дмитрий", 27));

        String uniqueNames = people.stream().map(Person::getName).distinct().collect(Collectors.joining(", "));
        System.out.println("Уникальные имена: " + uniqueNames + ".");
        System.out.println();

        double averageAgeUnder18 = people.stream().filter(x -> x.getAge() < 18).collect(Collectors.averagingDouble(Person::getAge));
        System.out.println("Средний возразт людей младше 18 лет: " + averageAgeUnder18 + " лет.");
        System.out.println();

        Map<String, Double> averageAgeByName = people.stream().collect(Collectors.groupingBy(Person::getName, Collectors.averagingDouble(Person::getAge)));
        System.out.println("Средний возразт носителей имен: " + averageAgeByName);
        System.out.println();

        List<Person> from20To45 = people.stream().filter(x -> x.getAge() >= 20 && x.getAge() <= 45).
                sorted((p1, p2) -> p2.getAge() - p1.getAge()).collect(Collectors.toList());
        System.out.println(from20To45);
        System.out.println();

        //Задача 2
        int number;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите количество требуемых корней чисел: ");
            number = scanner.nextInt();

            System.out.println();
        }

        Stream<Double> squareRoots = Stream.iterate(0, x -> x + 1).map(Math::sqrt).limit(number);
        List<Double> list = squareRoots.collect(Collectors.toList());
        System.out.println(list);
    }
}