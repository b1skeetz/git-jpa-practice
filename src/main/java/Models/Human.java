package Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "people")
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Override
    public String toString() {
        return String.format("Имя: %s, возраст: %d, адрес: %s", name, age, address);
    }
}