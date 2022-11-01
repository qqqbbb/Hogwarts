package qqqbbb.hogwarts.school.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return getAge() == student.getAge() && getId() == student.getId() && getName().equals(student.getName());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getName(), getAge());
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

}
