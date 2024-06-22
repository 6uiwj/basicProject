package org.choongang.commons;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.service.ConfigInfoService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//모든 컨트롤러에 사이트 설정 유지 (공통기능공유)
//템플릿 쪽에서 bean의 이름은 class명
@ControllerAdvice("org.choongang")
@RequiredArgsConstructor
public class BasicConfigAdvice {
    private final ConfigInfoService infoService;

    @ModelAttribute("siteConfig")
    public Map<String, String> getBasicConfig() {
        Optional<Map<String, String>> opt = infoService.get("basic",
                new TypeReference<>() {
                });

        Map<String, String> config = opt.orElseGet(() -> new HashMap<>());
        return config;
    }


}
