package org.choongang.admin.config.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.entities.Configs;
import org.choongang.admin.config.repositories.ConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

//조회 서비스
@Service
@RequiredArgsConstructor
public class ConfigInfoService {
    //Json형태를 원래 형태로 바꿔줘야 하기 때문에 generic 형태 필요

    private final ConfigRepository repository;
    //단순한 구조일 때 (basicConfig 객체 등..)
    //메서드 오버로드
    public <T> T get(String code, Class<T> clazz) {
        //typereference가 null값이면 처리하지 않고 class class가 있으면 처리
        return get(code, clazz, null);
    }

    //복합적 구조일 때(map, list 등)
    //메서드 오버로드
    public <T> T get(String code, TypeReference<T> typeReference) {
        //class class가 null값이면 typereference가 처리
        return get(code, null, typeReference);
    }

    //복합적인 경우 clazz가 아닌 typereference로 처리해야 함
    public <T> T get(String code, Class<T> clazz, TypeReference<T> typeReference) {
        Configs config = repository.findById(code).orElse(null);
        //config가 null일 때, 공백일 때는 처리X
        if(config==null||!StringUtils.hasText(config.getData())) {
            return null;
        }
        //변환작업
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        String jsonString = config.getData();
        //Json 형태를 원래 형태로 반환?
        try {
            T data = null;
            if (clazz==null) { //TypeReference로 처리
                data = om.readValue(jsonString, new TypeReference<T>() {});
            } else { // Clas로 처리
                data = om.readValue(jsonString, clazz);
            }
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();;
            return null;
        }
    }
}
