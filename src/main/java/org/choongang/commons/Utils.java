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
    private final HttpSession session; //session에 "mobile" 문자가 있는지 확인하기 위해(강제수동전환 위해)

    //현재 상태가 PC인지 모바일인지 체크하는 메서드
    public boolean isMobile() {
        /*모바일 수동 전환 모드 체크
        장비를 확인해서 pattern에 해당사항이 있으면 mobile로 취급하고,
        만약 장비에 해당사항이 없더라도 session 값에 "mobile"이라는 문자가 있으면 모바일로 취급한다.(PC에서 mobile로 접근하려 할 경우 강제로 수동 mobile 전환) */
        String device = (String)session.getAttribute("device");
        if (StringUtils.hasText(device)) {
            return device.equals("MOBILE"); //값이 있으면 모바일 수동전환, 없으면 PC수동전환
        }


        //요청 헤더 중 'User-Agent'패턴을 가지고 모바일인지 체크
        //HttpServletRequest에서 조회 (의존성 주입 필요)
        //헤더 가져오기
        String ua = request.getHeader("User-Agent");


        //아래와 일치하는 장비가 있다면 true 반환
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

        return ua.matches(pattern);



    }

    //장비에 따라 경로 설정해주기
    public String tpl(String path) {
        //mobile장비면 mobile/, PC장비면 front/로 경로 시작
        String prefix = isMobile() ? "mobile/" : "front/";
        return prefix + path;
    }
}
