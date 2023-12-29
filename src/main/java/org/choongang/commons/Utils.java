package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;
    private final HttpSession session;

    /**
     * 모바일인지 확인하는 편의기능
     */
    public boolean isMobile() {
        // 모바일 수동 모드 전화 체크
        String device = (String)seesion.getAttribute("device");
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
}
