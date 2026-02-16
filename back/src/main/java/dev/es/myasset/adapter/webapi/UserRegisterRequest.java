package dev.es.myasset.adapter.webapi;

import jakarta.validation.constraints.NotNull;

public record UserRegisterRequest(
        @NotNull(message = "회원가입에 대한 동의가 필요합니다.") boolean registerAgreement
) {}
