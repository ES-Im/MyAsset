package dev.es.myasset.domain.user;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE
    , WITHDRAWN
    , WITHDRAW_PENDING
    , DORMANT
}
