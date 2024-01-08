package org.choongang.file.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final FileInfoService infoService;
    private final HttpServletResponse response;

    public void download(Long seq) {
        FileInfo data = infoService.get(seq);
        String filePath = data.getFilePath();

        // DB 한글 = 기본 3바이트, 파일명 -> 2바이트 인코딩 변경(윈도으즈 시스템에서 한글꺠짐방지)
        String fileName = null;
        try {
            fileName = new String(data.getFileName().getBytes(), "ISO8859_1");
        } catch (UnsupportedEncodingException e) {}

        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            OutputStream out = response.getOutputStream(); // 응답 Body에 출력

            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setHeader("Content-Type", "application/octet-stream");
            response.setIntHeader("Expires", 0);  // 만료시간 x
            response.setHeader("Cache-Control", "must-revalidate");  // 캐시컨트롤 - 최신브라우저
            response.setHeader("Pragma", "public");  // 캐시컨트롤 - 과거브라우저
            response.setHeader("Content_Length", String.valueOf(file.length()));  // 파일 용량

            while (bis.available() > 0) {
                out.write(bis.read());
            }

            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
