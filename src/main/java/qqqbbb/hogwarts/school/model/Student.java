package qqqbbb.hogwarts.school.model;

import org.springframework.beans.factory.annotation.Value;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Students")
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

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

    @Override
    public String toString()
    {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", house=" + house +
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

    public int getAge()
    {
        return age;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public void setAvatarsDir(String avatarsDir)
    {
        this.avatarsDir = avatarsDir;
    }

    public void setHouse(House house)
    {
        this.house = house;
    }

}
