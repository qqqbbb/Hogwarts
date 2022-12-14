package qqqbbb.hogwarts.school.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Houses")
public class House
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String color;

    @OneToMany(mappedBy = "house")
    private Set<Student> students;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return getId() == house.getId() && getName().equals(house.getName());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString()
    {
        return "House{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getColor()
    {
        return color;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
}

