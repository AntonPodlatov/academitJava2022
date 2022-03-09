package ru.academits.podlatov.lambdas.main;

import ru.academits.podlatov.lambdas.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>(Arrays.asList(
                new Person("Леонид", 12),
                new Person("Николай", 23),
                new Person("Константин", 1),
                new Person("Борис", 30),
                new Person("Анна", 17),
                new Person("Николай", 45),
                new Person("Дмитрий", 65),
                new Person("Иван", 19),
                new Person("Дмитрий", 27)));

        String uniqueNames = people.stream()
                .map(Person::getName)
                .distinct()
                .collect(Collectors.joining(", "));

        System.out.println("Уникальные имена: " + uniqueNames + ".");
        System.out.println();

        double averageAgeUnder18 = people.stream()
                .filter(x -> x.getAge() < 18)
                .collect(Collectors.averagingDouble(Person::getAge));

        System.out.println("Средний возраст людей младше 18 лет: " + averageAgeUnder18 + " лет.");
        System.out.println();

        Map<String, Double> averageAgeByName = people.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingDouble(Person::getAge)));

        System.out.println("Средний возраст носителей имен: " + averageAgeByName);
        System.out.println();

        List<Person> peopleAged20To45;
        peopleAged20To45 = people.stream()
                .filter(x -> x.getAge() >= 20 && x.getAge() <= 45)
                .sorted((p1, p2) -> p2.getAge() - p1.getAge()).collect(Collectors.toList());

        System.out.println("Люди в возрасте от 20 до 45 лет по убыванию возраста: " + peopleAged20To45);
        System.out.println();

        //Задача 2
        int number;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество требуемых корней чисел: ");
        number = scanner.nextInt();
        System.out.println();

        Stream<Double> squareRoots = Stream.iterate(0, x -> x + 1)
                .map(Math::sqrt)
                .limit(number);

        List<Double> list = squareRoots.collect(Collectors.toList());
        System.out.println(list);
    }
}