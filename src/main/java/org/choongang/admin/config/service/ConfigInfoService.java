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

import java.util.Optional;

//조회 서비스
@Service
@RequiredArgsConstructor
public class ConfigInfoService {
    //Json형태를 원래 형태로 바꿔줘야 하기 때문에 generic 형태 필요

    private final ConfigRepository repository;
    //단순한 구조일 때 (basicConfig 객체 등..) = 구조 1개!
    //class class 형태
    //메서드 오버로드
    //반환값이 null이면 오류가 발생할 수도 있으므로 Optional로 처리
    public <T> Optional<T> get(String code, Class<T> clazz) {
        //typereference가 null값이면 처리하지 않고 class class가 있으면 처리
        return get(code, clazz, null);
    }

    //복합적 구조일 때(map, list 등)
    //TypeReference
    //메서드 오버로드
    //반환값이 null이면 오류가 발생할 수도 있으므로 Optional로 처리
    public <T> Optional<T> get(String code, TypeReference<T> typeReference) {
        //class class가 null값이면 typereference가 처리
        return get(code, null, typeReference);
    }

    //복합적인 경우 clazz가 아닌 typereference로 처리해야 함
    //반환값이 null이면 오류가 발생할 수도 있으므로 Optional로 처리
    public <T> Optional<T> get(String code, Class<T> clazz, TypeReference<T> typeReference) {
        Configs config = repository.findById(code).orElse(null);
        //config가 null일 때, 공백일 때는 처리X
        if(config==null||!StringUtils.hasText(config.getData())) {
            return Optional.ofNullable(null);
        }
        //변환작업
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        String jsonString = config.getData();
        //Json 형태를 원래 형태로 반환?
        try {
            T data = null;
            if (clazz==null) { //clazz가 null이면 TypeReference로 처리
                data = om.readValue(jsonString, new TypeReference<T>() {});
            } else { // TypeReference가 null이면 Clas로 처리
                data = om.readValue(jsonString, clazz);
            }
            return Optional.ofNullable(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();;
            return Optional.ofNullable(null);
        }
    }
}
