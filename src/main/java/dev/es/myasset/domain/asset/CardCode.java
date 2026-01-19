package dev.es.myasset.domain.asset;

import lombok.Getter;

@Getter
public enum CardCode {
    TEST_CARD("000", "테스트카드")
    , IBK_BC("03K", "기업비씨")
    , GWANGJUBANK("046", "광주")
    , LOTTE("071", "롯데")
    , KDBBANK("030", "산업")
    , BC("031", "-")
    , SAMSUNG("051", "삼성")
    , SAEMAUL("038", "새마을")
    , SHINHAN("041", "신한")
    , SHINHYEOP("062", "신협")
    , CITI("036", "씨티")
    , WOORI("033", "우리")
    , POST("037", "우체국")
    , SAVINGBANK("039", "저축")
    , JEONBUKBANK("035", "전북")
    , JEJUBANK("042", "제주")
    , KAKAOBANK("015", "카카오뱅크")
    , KBANK("03A", "케이뱅크")
    , TOSSBANK("024", "토스뱅크")
    , HANA("021", "하나")
    , HYUNDAI("061", "현대")
    , KOOKMIN("011", "국민")
    , NONGHYEOP("091", "농협")
    , SUHYEOP("034", "수협")
    , DINERS("06D", "다이너스")
    , MASTER("04M", "마스터")
    , UNIONPAY("03C", "유니온페이")
    , AMEX("07A", "-")
    , JCB("04J", "-");

    private String code;
    private String name;

    CardCode(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
