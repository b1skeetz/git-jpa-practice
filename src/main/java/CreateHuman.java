import Models.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class CreateHuman {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите имя человека: ");
        String searchName = scanner.nextLine();
        try {
            TypedQuery<Human> searchForHuman = manager.createQuery("select h from Human h where h.name = ?1", Human.class);
            searchForHuman.setParameter(1, searchName);
            Human foundHuman = searchForHuman.getSingleResult();
            System.out.println(foundHuman);
            return;
        } catch (NoResultException e){
            System.out.println("Пользователь с таким именем не найден!\nСоздать запись нового человека? [y/n]");
            String answer = scanner.nextLine();
            switch (answer) {
                case "y", "yes" -> System.out.println("Создание пользователя:");
                case "n", "no" -> {
                    System.out.println("Создание отклонено");
                    return;
                }
                default -> {
                    System.out.println("Неверный ответ!");
                    System.out.println("Создание отклонено");
                    return;
                }
            }
        }


        System.out.println("Выберите город: ");
        TypedQuery<City> selectAllCities = manager.createQuery("select c from City c", City.class);
        List<City> citiesList = selectAllCities.getResultList();
        for (City city : citiesList) {
            System.out.println(city.getId() + ") " + city.getName() + " (" + city.getCountry() + ").");
        }
        Integer chosenId = Integer.parseInt(scanner.nextLine());
        City chosenCity = citiesList.get(chosenId - 1);

        /*System.out.print("Введите имя: ");*/
        String name = searchName;

        System.out.print("Введите возраст: ");
        Integer age = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите адрес: ");
        String address = scanner.nextLine();

        Human human = new Human();
        human.setName(name);
        human.setAge(age);
        human.setAddress(address);
        human.setCity(chosenCity);

        try {
            manager.getTransaction().begin();
            manager.persist(human);
            manager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            manager.getTransaction().rollback();
            throw new RuntimeException();
        }
        manager.close();
        factory.close();
    }
}