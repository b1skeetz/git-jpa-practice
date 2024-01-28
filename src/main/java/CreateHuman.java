import Models.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Scanner;

public class CreateHuman {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите город: ");
        TypedQuery<City> selectAllCities = manager.createQuery("select c from City c", City.class);
        List<City> citiesList = selectAllCities.getResultList();
        for (City city : citiesList) {
            System.out.println(city.getId() + ") " + city.getName() + " (" + city.getCountry() + ").");
        }
        Integer chosenId = Integer.parseInt(scanner.nextLine());
        City chosenCity = citiesList.get(chosenId - 1);

        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите возраст: ");
        Integer age = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите адрес: ");
        String address = scanner.nextLine();

        Human human = new Human();
        human.setName(name);
        human.setAge(age);
        human.setAddress(address);
        human.setCity(chosenCity);

        try{
            manager.getTransaction().begin();
            manager.persist(human);
            manager.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
            manager.getTransaction().rollback();
            throw new RuntimeException();
        }
        manager.close();
        factory.close();
    }
}