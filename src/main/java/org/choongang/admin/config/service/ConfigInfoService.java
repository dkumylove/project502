package org.choongang.admin.config.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.entities.Configs;
import org.choongang.admin.config.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfigInfoService {

    private final ConfigsRepository repository;
    public <T> Optional<T> get(String code, Class<T> clazz) {
        return get(code, clazz, null);
    }

    public <T> Optional<T> get(String code, TypeReference<T> typeReference) {
        return get(code, null, typeReference);
    }

    /**
     * 제네릭 사용 이유 : 어떤 값이 올지 알수 없기 떄문
     * 원래의 타입으로 변환 필요
     * @param code
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> Optional<T> get(String code, Class<T> clazz, TypeReference<T> typeReference) {

        /**
         * Class<T> clazz : 단일 클래스일떄
         * TypeReference<T> typeReference : 복잡한 형태 (Map, List, 중첩, 컬랙션 등)
         * Optional : null처리함
         * jsonString : JSON 문자열
         * readValue : 문자열을 자바 객체로 변경
         */

        Configs config = repository.findById(code).orElse(null);
        if(config == null || !StringUtils.hasText(config.getData())) {
            return Optional.ofNullable(null);
        }
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        String jsonString = config.getData();
        try {
            T data = null;
            if(clazz == null) {  // TypeReference로 처리
                data= om.readValue(jsonString, new TypeReference<T>() {});
            } else {  // Class로 처리
                data = om.readValue(jsonString, clazz);
            }
            return Optional.ofNullable(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Optional.ofNullable(null);
        }
    }
}
