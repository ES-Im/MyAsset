package dev.es.myasset.domain.asset;

import lombok.Getter;

@Getter
public enum CompanyCode {
    MockBank("aaa", "테스트은행증권사")     // 테스트용
    , KYONGNAMBANK("039", "경남")
    , GWANGJUBANK("034", "광주")
    , KYOBO_SECURITIES("261", "교보증권")
    , LOCALNONGHYEOP("012", "단위농협")
    , DAISHIN_SECURITIES("267", "대신증권")
    , MERITZ_SECURITIES("287", "메리츠증권")
    , MIRAE_ASSET_SECURITIES("238", "미래에셋증권")
    , BOOKOOK_SECURITIES("290", "부국")
    , BUSANBANK("032", "부산")
    , SAMSUNG_SECURITIES("240", "삼성증권")
    , SAEMAUL("045", "새마을")
    , SANLIM("064", "산림")
    , SHINYOUNG_SECURITIES("291", "신영증권")
    , SHINHAN_INVESTMENT("278", "신한금융투자")
    , SHINHAN("088", "신한")
    , SHINHYEOP("048", "신협")
    , CITI("027", "씨티")
    , WOORI("020", "우리")
    , POST("071", "우체국")
    , YUANTA_SECURITES("209", "유안타증권")
    , EUGENE_INVESTMENT_AND_SECURITIES("280", "유진투자증권")
    , SAVINGBANK("050", "저축")
    , JEONBUKBANK("037", "전북")
    , JEJUBANK("035", "제주")
    , KAKAOBANK("090", "카카오")
    , KAKAOPAY_SECURITIES("288", "카카오페이증권")
    , KBANK("089", "케이")
    , KIWOOM("264", "키움증권")
    , TOSS_MONEY("00-", "토스머니")
    , TOSSBANK("092", "토스")
    , TOSS_SECURITIES("271", "토스증권")
    , KOREA_FOSS_SECURITIES("294", "펀드온라인코리아")
    , HANA_INVESTMENT_AND_SECURITIES("270", "하나금융투자")
    , HANA("081", "하나")
    , HI_INVESTMENT_AND_SECURITIES("262", "하이투자증권")
    , KOREA_INVESTMENT_AND_SECURITIES("243", "한국투자증권")
    , HANHWA_INVESTMENT_AND_SECURITIES("269", "한화투자증권")
    , HYUNDAI_MOTOR_SECURITIES("263", "현대차증권")
    , HSBC("054", "-")
    , DB_INVESTMENT_AND_SECURITIES("279", "DB금융투자")
    , DAEGUBANK("031", "대구")
    , IBK("003", "기업")
    , KOOKMIN("004", "국민")
    , KB_SECURITIES("218", "KB증권")
    , KDBBANK("002", "산업")
    , DAOL_INVESTMENT_AND_SECURITIES("227", "KTB투자증권")
    , LIG_INVESTMENT_AND_SECURITIES("292", "LIG투자")
    , NONGHYEOP("011", "농협")
    , NH_INVESTMENT_AND_SECURITIES("247", "NH투자증권")
    , SC("023", "SC제일")
    , SUHYEOP("007", "수협")
    , SK_SECURITIES("266", "SK증권");


    private final String code;
    private final String name;

    CompanyCode(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
