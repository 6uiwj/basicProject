package org.choongang.admin.config.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public <T> T get(String code, Class<T> clazz) {
        Configs config = repository.findById(code).orElse(null);
        //config가 null일 때, 공백일 때는 처리X
        if(config==null||!StringUtils.hasText(config.getData())) {
            return null;
        }
        //변환작업
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        String jsonString = config.getData();
        //Jason 형태를 원래 형태로 반환?
        try {
            T data = om.readValue(jsonString, clazz);
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();;
            return null;
        }
    }
}
