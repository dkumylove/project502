package org.choongang.commons;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.controllers.BasicConfig;
import org.choongang.admin.config.service.ConfigInfoService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice("org.choongang")
@RequiredArgsConstructor
public class BasicConfigAdvice {

    private final ConfigInfoService infoService;

    /*
    @ModelAttribute("siteConfig")
    public Map<String, String> getBasicConfig() {
        Optional<Map<String, String>> opt = infoService.get("basic", new TypeReference<>() {});
        Map<String, String> config = opt.orElseGet(() -> new HashMap<>());
        System.out.println("config = " + config);
        return config;
    }
    */

    /**
     * ConfigInfoService 반환값이 Optional 형태
     * Optional 사용 null처리에 용이
     * @return
     */
    @ModelAttribute("siteConfig")
    public BasicConfig getBasicConfig() {
        BasicConfig config = infoService.get("basic", BasicConfig.class).orElseGet(BasicConfig::new);

        return config;
    }


}
