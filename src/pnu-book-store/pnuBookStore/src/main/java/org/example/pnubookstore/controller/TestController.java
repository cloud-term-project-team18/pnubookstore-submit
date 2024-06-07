package org.example.pnubookstore.controller;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.core.s3.S3Uploader;
import org.example.pnubookstore.core.security.CustomUserDetails;
import org.example.pnubookstore.domain.product.entity.Subject;
import org.example.pnubookstore.domain.product.repository.SubjectCustomRepositoryImpl;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/image")
public class TestController {

    private final S3Uploader s3Uploader;
    private final SubjectCustomRepositoryImpl subjectCustomRepository;
    @PostMapping(
            value = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadThumbnail(MultipartFile imageFile
                                                  ) throws IOException {
//        userDetails.getUser();
//        @AuthenticationPrincipal CustomUserDetails userDetails
        String url = s3Uploader.upload(imageFile, "images");
        return ResponseEntity.ok(url);
    }

    @GetMapping(value = "/test/api/subjects")
    public ResponseEntity<List<Subject>> findSubjects(@RequestParam(value = "college") String college,
                                                      @RequestParam(value = "department", required = false) String department,
                                                      @RequestParam(value = "professor", required = false) String professor,
                                                      @RequestParam(value = "course", required = false) String subjectName){
        List<Subject> subjects = subjectCustomRepository.findSubjects(
                college, department, professor, subjectName);

        return ResponseEntity.ok(subjects);
    }
}

