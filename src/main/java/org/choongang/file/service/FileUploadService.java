package org.choongang.file.service;


import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.choongang.commons.Utils;
import org.choongang.configs.FileProperties;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.repositories.FileInfoRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileUploadService {

    private final FileProperties fileProperties;
    private final FileInfoRepository repository;
    private final FileInfoService infoService;
    private final Utils utils;

    // DB에 올라가면, 파일을 리스트 형태로 반환값 만듬
    public List<FileInfo> upload(MultipartFile[] files, String gid, String location) {
        /**
         * 1. 파일 정보 저장
         * 2. 서버쪽에 파일 업로드 처리
         */

        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString();

        //  파일 없로드 기본 경로
        String uploadPath = fileProperties.getPath();
        System.out.println(fileProperties);

        // 썹네일 업로드 기본경로
        String thumbPath = uploadPath + "thumbs/";

        // 썸네일 사이즈
        List<int[]> thumbsSize = utils.getThumbSize();

        // 업로드 성공 파일 정보 목록
        List<FileInfo> uploadedFiles = new ArrayList<>();

        for (MultipartFile file : files) {

            /* 파일정보 저장 s */

            String fileName = file.getOriginalFilename();  // 업로드시 원 파일멍
            // 파일명.확장자 image.png

            // 확장자 : 원본 파일의 맨뒤에서 .으로 찾은
            String extension = fileName.substring(fileName.lastIndexOf("."));

            // 파일종류 ex) image/...
            String fileType = file.getContentType();

            FileInfo fileInfo = FileInfo.builder()
                    .gid(gid)
                    .location(location)
                    .fileName(fileName)
                    .extension(extension)
                    .fileType(fileType)
                    .build();

            repository.saveAndFlush(fileInfo);
            /* 1. 파일정보 저장 e */

            /* 2. 서버쪽에 파일 업로드 처리 s */
            long seq = fileInfo.getSeq();
            File dir = new File(uploadPath + (seq % 10));
            if (!dir.exists()) {  // 디렉토리가 없으면 - > 생성
                dir.mkdir();
            }

            // 실제 파일 경로
            File uploadFile = new File(dir, seq + extension);
            try {
                file.transferTo(uploadFile);

                /* 썸네일 이미지 처리 s */
                if (fileType.indexOf("image/") != -1 && thumbsSize != null) {
                    File thumbDir = new File(thumbPath + (seq % 10L) + "/" + seq);
                    if(!thumbDir.exists()) {
                        thumbDir.mkdirs();
                    }
                    for(int[] sizes : thumbsSize) {
                        String thumbFileName = sizes[0] + "_" + sizes[1] + "_" + seq + extension;

                        File thumb = new File(thumbDir, thumbFileName);
                        Thumbnails.of(uploadFile)
                                .size(sizes[0], sizes[1])
                                .toFile(thumb);
                    }
                }

                /* 썸네일 이미지 처리 e */

                infoService.addFileInfo(fileInfo);  // 파일 추가 정보 처리
                uploadedFiles.add(fileInfo);  // 업로드 성공시 파일 정보 추가
            } catch (IOException e) {
                e.printStackTrace();
                repository.delete(fileInfo); // 업로드 실패시에는 파일 정보 제거
                repository.flush();
            }
            /* 2. 서버쪽에 파일 업로드 처리 e */
        }
        return uploadedFiles;
    }

    /**
     * 업로드 완료처리
     */
    public void processDone(String gid) {
        List<FileInfo> files = repository.findByGid(gid);

        if(files == null) {
            return;
        }

        files.forEach(file -> file.setDone(true));
        repository.flush();
    }
}
