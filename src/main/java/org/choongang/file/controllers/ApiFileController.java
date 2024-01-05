package org.choongang.file.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionRestProcessor;
import org.choongang.commons.rests.JSONData;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileUploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * rest 컨트롤러 : API 붙여서 통일
 */

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class ApiFileController implements ExceptionRestProcessor {

    private final FileUploadService uploadService;

    @PostMapping
    public JSONData<List<FileInfo>> upload(@RequestParam("file") MultipartFile[] files,
                                           @RequestParam(name="gid", required = false) String gid,
                                           @RequestParam(name="location", required = false) String location, @RequestParam(name = "imageOnly", required = false) boolean imageOnly) {

        List<FileInfo> uploadedFiles = uploadService.upload(files, gid, location, imageOnly);

        return new JSONData<>(uploadedFiles);
    }

    @GetMapping("/{seq}")
    public void delete(@PathVariable("seq") Long seq) {

    }
}
