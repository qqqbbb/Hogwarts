package qqqbbb.hogwarts.school.service;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qqqbbb.hogwarts.school.Exception.AvatarNotFoundException;
import qqqbbb.hogwarts.school.model.*;
import qqqbbb.hogwarts.school.repository.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
//@Transactional
public class AvatarService
{
    Logger logger = LoggerFactory.getLogger(AvatarService.class);
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository)
    {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }
    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException
    {
        logger.info("uploadAvatar " + studentId);
        Student student = studentService.getStudent(studentId);
        Path filePath = Path.of(avatarsDir, student + "." + getFileNameExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (   InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024))
        {
            bis.transferTo(bos);
        }
        Optional<Avatar> optional = avatarRepository.findByStudentId(studentId);
        Avatar avatar = optional.orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
    }

    public Avatar getAvatar(Long studentId)
    {
        logger.info("getAvatar " + studentId);
        Optional<Avatar> optionalAvatar = avatarRepository.findByStudentId(studentId);
        return optionalAvatar.orElseThrow(AvatarNotFoundException::new);
    }

    public ResponseEntity<byte[]> downloadAvatarFromDB(Long studentId)
    {
        logger.info("downloadAvatarFromDB " + studentId);
        Avatar avatar = getAvatar(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    public void downloadAvatarFromFile(long studentId, HttpServletResponse response) throws IOException
    {
        logger.info("downloadAvatarFromFile " + studentId);
        Avatar avatar = getAvatar(studentId);
        Path path = Path.of(avatar.getFilePath());
        try(InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream())
        {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    private String getFileNameExtension(String fileName)
    {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> getAll(Pageable paging)
    {
        logger.info("getAll ");
        Page<Avatar> pagedResult = avatarRepository.findAll(paging);
        if(pagedResult.hasContent())
            return pagedResult.getContent();
         else
            return new ArrayList<Avatar>();
    }
}
