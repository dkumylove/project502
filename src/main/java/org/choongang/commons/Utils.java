package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.controllers.BasicConfig;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;
    private final HttpSession session;

    private static final ResourceBundle commonsBundle;
    private static final ResourceBundle errorsBundle;
    private static final ResourceBundle validationsBundle;

    // static 초기화
    static {
        commonsBundle = ResourceBundle.getBundle("messages.commons");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
        validationsBundle = ResourceBundle.getBundle("messages.validations");
    }

    /**
     * 모바일인지 확인하는 편의기능
     */
    public boolean isMobile() {
        // 모바일 수동 전환 모드 체크
        String device = (String)session.getAttribute("device");
        if (StringUtils.hasText(device)) {
            return device.equals("MOBILE");
        }

        // 요청 헤더 : User-Agent
        String ua = request.getHeader("User-Agent");

        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

        return ua.matches(pattern);
    }

    public String tpl(String path) {
        String prefix = isMobile() ? "mobile/" : "front/";

        return prefix + path;
    }

    /**
     * resources.messages 가져오기
     */
    public static String getMessage(String code, String type) {
        // 기본값 validations 고정
        type = StringUtils.hasText(type) ? type : "validations";

        ResourceBundle bundle = null;
        if(type.equals("commons")) {
            bundle = commonsBundle;
        } else if (type.equals("errors")) {
            bundle = errorsBundle;
        } else {
            bundle = validationsBundle;
        }

        return bundle.getString(code);
    }

    public static String getMessage(String code) {
        return  getMessage(code, null);
    }

    /**
     * 줄 개행
     * \n 또는 \r\n -> <br>
     */
    public String nl2br(String str) {
        str = str.replaceAll("\\n", "<br>")
                .replaceAll("\\r", "");
        return str;
    }

    /**
     * 썸네일 이미지 사이즈 설정
     */
    public List<int[]> getThumbSize() {
        BasicConfig config = (BasicConfig) request.getAttribute("siteConfig");
        String thumbSize = config.getThumbSize();  // \r\n
        String[] thumbsSize = thumbSize.split("\\n"); // 자르기

        List<int[]> data = Arrays.stream(thumbsSize).map(this::toConvert).toList();
        return null;
    }

    public int[] toConvert(String size) {

        size = size.trim();

        int[] data = Arrays.stream(size.replaceAll("\\r", "").toUpperCase().split("X")).mapToInt(Integer::parseInt).toArray();

        return data;
    }

}
