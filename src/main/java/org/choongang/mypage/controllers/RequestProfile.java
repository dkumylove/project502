package org.choongang.mypage.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.choongang.file.entities.FileInfo;

/**
 * 바꿔야하는 데이터만 정의하면 됨
 */
@Data
public class RequestProfile {

    @NotBlank
    private String name;

    private String password;

    private String confirmPassword;

    private FileInfo profileImage;

}
