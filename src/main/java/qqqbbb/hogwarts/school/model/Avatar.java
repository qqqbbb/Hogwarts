package qqqbbb.hogwarts.school.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
//    @Lob
    private byte[] data;
    @OneToOne
    private Student student;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return getFileSize() == avatar.getFileSize() && getId().equals(avatar.getId()) && getFilePath().equals(avatar.getFilePath()) && getMediaType().equals(avatar.getMediaType()) && Arrays.equals(getData(), avatar.getData()) && getStudent().equals(avatar.getStudent());
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public void setFileSize(long fileSize)
    {
        this.fileSize = fileSize;
    }

    public void setMediaType(String mediaType)
    {
        this.mediaType = mediaType;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(getId(), getFilePath(), getFileSize(), getMediaType(), getStudent());
        result = 31 * result + Arrays.hashCode(getData());
        return result;
    }

    public Long getId()
    {
        return id;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public long getFileSize()
    {
        return fileSize;
    }

    public String getMediaType()
    {
        return mediaType;
    }

    public byte[] getData()
    {
        return data;
    }

    public Student getStudent()
    {
        return student;
    }
}