package org.choongang.admin.config.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.entities.Configs;
import org.choongang.admin.config.repositories.ConfigRepository;
import org.springframework.stereotype.Service;

//테이블에 저장하는 서비스(있으면 수정, 없으면 추가)
@Service
@RequiredArgsConstructor
public class ConfigSaveService {
    private final ConfigRepository repository;

    //저장 처리 - 코드로 저장
    public void save(String code, Object data) {
        //조회 결과 데이터가 있으면 가져오고 없으면 비어있는 엔티티를 하나 생성
        //findById의 반환값: Optional
        Configs configs = repository.findById(code).orElseGet(Configs::new);

        //ObjectMapper: 자바객체를 Json 문자열로 반환
        ObjectMapper om = new ObjectMapper();
        //날짜와 시간 추가
        om.registerModule(new JavaTimeModule());

        //반환값은 Json, 예외처리
        try {
            //configs엔티티의 data필드가 Json형태이므로 Json으로 바꿔 준 후 저장
            String jsonString = om.writeValueAsString(data);
            configs.setData(jsonString);
            repository.saveAndFlush(configs);
        } catch (JsonProcessingException e) {
            //에러메시지는 출력
                e.printStackTrace();
        }
    }
}
