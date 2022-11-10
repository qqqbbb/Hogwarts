package qqqbbb.hogwarts.school.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import qqqbbb.hogwarts.school.model.Avatar;
import qqqbbb.hogwarts.school.service.AvatarService;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("avatar")
public class AvatarController
{
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService)
    {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile file) throws IOException
    {
        if (file.getSize() > 1024 * 500)
            return ResponseEntity.badRequest().body("FIle is too big");

        String contentType = file.getContentType();
        System.out.println("upload avatar contentType " + contentType);
        if (StringUtils.isBlank(contentType) || !contentType.contains("image"))
            return ResponseEntity.badRequest().body("Only images can be uploaded");

        avatarService.uploadAvatar(studentId, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{studentId}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable long studentId)
    {
        return avatarService.downloadAvatarFromDB(studentId);
    }

    @GetMapping(value = "/{studentId}/avatar-from-file")
    public void downloadAvatar(@PathVariable long studentId, HttpServletResponse response) throws IOException
    {
        avatarService.downloadAvatarFromFile(studentId, response);
    }

    @GetMapping("all")
    public ResponseEntity<List<Avatar>> getAllAvatars(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return new ResponseEntity<>(avatarService.getAl(pageable), HttpStatus.OK);
    }
}
