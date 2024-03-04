package org.choongang.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * prehandle, posthandle, aftercompletion
 *
 * 1.prehandle 컨트롤러 실행 전 공통처리내용 정의 -> return이 true인 이유는 시큐리티에서 처리해주기 때문?
 * 2.posthandle: 컨트롤러 실행 후, view에 출력 전 내용 처리
 * 3. aftercompetion: 컨트롤러 실행 및 view 출력 후 내용 처리
 *
 *
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {

    /**
     * 컨트롤러 실행 전 (컨트롤러 빈 실행되기 전 처리)
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        checkDevice(request);
        clearLoginData(request);

        return true;
    }


    /**
     * PC, 모바일 수동 변경 처리
     * //device가 PC면 PC view, Mobile이면 Mobile view로 수동 전환
     * @param request
     */
    private void checkDevice(HttpServletRequest request) {
        String device = request.getParameter("device"); //리퀘스트 객체에서 device정보 가져오기
        //값이 있을 때만 처리
        if(!StringUtils.hasText(device)) {
            return; //값이 없을 때에는 그냥 넘김
        }

        //mobile이 아니면 PC로 취급 (대소문자 구분없이 받기 -> 대문자로 변환)
        device = device.toUpperCase().equals("MOBILE") ? "MOBILE" : "PC";
        //값이 있으면 session에 넣고 고정
        HttpSession session = request.getSession();
        session.setAttribute("device", device);
    }

    /**
     *  로그인 페이지에서 다른 페이지 이동 시 로그인 데이터 비워주기(검증 메시지 초기화)
     *  request 필요 이유 :
     *  1. 접속 주소를 알기 위해(로그인 페이지인지? 아닌지? 구분 위해)
     *  2. session 객체 필요 (로그인 페이지가 아닐 때에는 세션을 지우기 위해
     * @param request
     */
    private void clearLoginData(HttpServletRequest request) {
        // 주소가 member/login인지 체크해보고, 아닐 때에는 세션 지우기
        String URL = request.getRequestURI();
        if(URL.indexOf("/member/login") == -1) { // -1 : 값이 없을 때
            HttpSession session = request.getSession();
            MemberUtil.clearLoginData(session); // 세션 비우기
        }
    }

}
