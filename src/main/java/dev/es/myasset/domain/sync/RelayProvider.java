package dev.es.myasset.domain.sync;

import lombok.Getter;

@Getter
public enum RelayProvider {
    
    MOCK0000("테스트용")
    , ZWAALV0000("한국정보통신진흥협회")
    , ZWAACP0000("코스콤")
    , ZWAAEA0000("금융결제원")
    , ZWAAAA0000("한국신용정보원");

    private final String name;

    RelayProvider(String name) {
        this.name = name;
    }
}
