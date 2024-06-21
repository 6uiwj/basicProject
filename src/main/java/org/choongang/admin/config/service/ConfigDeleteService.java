package org.choongang.admin.config.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.entities.Configs;
import org.choongang.admin.config.repositories.ConfigRepository;
import org.springframework.stereotype.Service;
//삭제 서비스
@Service
@RequiredArgsConstructor
public class ConfigDeleteService {
    private final ConfigRepository repository;

    //코드를 가지고 삭제
    public void delete(String code) {
        Configs config = repository.findById(code).orElse(null);
        //null이 아닐 때만 삭제 (null일 때는 return해서 코드가 실행되지 않게 해줌 )
        if (config == null) {
            return;
        }

        repository.delete(config);
        repository.flush();
    }
}
