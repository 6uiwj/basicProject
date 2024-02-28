package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;
    private final HttpSession session; //session에 "mobile" 문자가 있는지 확인하기 위해(강제수동전환 위해)

    //메시지 번들 가져오기
    private static final ResourceBundle commonsBundle;
    private static final ResourceBundle validationsBundle;
    private static final ResourceBundle errorsBundle;

    //메시지 번들 초기화( 객체가 만들어지지 않아도 클래스가 로드될 때 실행되도록)
    static {
        commonsBundle = ResourceBundle.getBundle("messages.commons");
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
    }

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



    /**
     * 각 항목에 따라 메시지 코드를 선별적으로 가져올 수 있도록 넣고,
     * 코드를 조회할 수 있도록
     * 객체를 만들지 않아도 조회할 수 있도록 정적 메서드로 추가
     * 스프링 관리객체가 아니더라도 사용가능하게 하도록 구성
     * properties파일들을 킷값을 통해 가져올 수 있도록
     *
     * @param code : 메시지 킷값
     * @param type : bundle type (commons ? errors ? validations ?)
     * @return : 키를 통해 메시지 조회
     */
    public static String getMessage(String code, String type) {
        //type이 null이나 비어있을 때에는 validations로 고정
        type = StringUtils.hasText(type) ? type : "validations";
        //타입에 따라 메시지 코드를 가져올 수 있도록 설정
        ResourceBundle bundle = null;
        if (type.equals("commons")) {
            bundle = commonsBundle;
        } else if (type.equals("errors")) {
            bundle = errorsBundle;
        } else {
            bundle = validationsBundle;
        }
        return bundle.getString(code); //code(키)를 통해 조회
    }


    /**
     * 코드만 입력하는 경우 기본적으로 validations로 갈 수 있도록
     * (validations가 제일 많이 쓰이기 때문에)
     * @param code : 메시지 킷값
     * @return
     */
    public static String getMessage(String code) {
        return getMessage(code, null);
    }
}
