package org.choongang.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

//컨트롤러 실행 전 공통처리내용 정의
@Component
public class CommonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        checkDevice(request);
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

}
