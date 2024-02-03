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
        String answer;
        try {
            TypedQuery<Human> searchForHuman = manager.createQuery("select h from Human h where h.name = ?1", Human.class);
            searchForHuman.setParameter(1, searchName);
            Human foundHuman = searchForHuman.getSingleResult();
            System.out.println("Пользователь найден!\nИзменить запись? [y/n]");
            answer = scanner.nextLine();
            switch (answer) {
                case "y", "yes" -> System.out.println("Изменение пользователя:");
                case "n", "no" -> {
                    System.out.println("Изменение отклонено");
                    System.out.println(foundHuman);
                    return;
                }
                default -> {
                    System.out.println("Неверный ответ!");
                    System.out.println("Изменение отклонено");
                    return;
                }
            }

            System.out.println("Выберите новый город: ");
            TypedQuery<City> selectAllCitiesUpdate = manager.createQuery("select c from City c", City.class);
            List<City> citiesList = selectAllCitiesUpdate.getResultList();
            for (City city : citiesList) {
                System.out.println(city.getId() + ") " + city.getName() + " (" + city.getCountry() + ").");
            }
            String isIdNull = scanner.nextLine();
            Long cityId;
            City chosenCity;
            if(isIdNull.equals("")){
                chosenCity = foundHuman.getCity();
            } else {
                cityId = Long.parseLong(isIdNull);
                chosenCity = citiesList.get((int) (cityId - 1));
            }

            System.out.print("Введите новое имя: ");
            String name = scanner.nextLine();
            if(name.equals("")){
                name = foundHuman.getName();
            }

            System.out.print("Введите новый возраст: ");
            String age = scanner.nextLine();
            Integer updateAge;
            if(age.equals("")){
                updateAge = foundHuman.getAge();
            } else {
                updateAge = Integer.parseInt(age);
            }

            System.out.print("Введите адрес: ");
            String address = scanner.nextLine();
            if(address.equals("")){
                address = foundHuman.getAddress();
            }
            try{
                manager.getTransaction().begin();
                foundHuman.setCity(chosenCity);
                foundHuman.setName(name);
                foundHuman.setAge(updateAge);
                foundHuman.setAddress(address);
                manager.getTransaction().commit();
            } catch (RuntimeException e){
                System.out.println(e.getMessage());
                manager.getTransaction().rollback();
                throw new RuntimeException();
            }
            return;
        } catch (NoResultException e){
            System.out.println("Пользователь с таким именем не найден!\nСоздать запись нового человека? [y/n]");
            answer = scanner.nextLine();
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