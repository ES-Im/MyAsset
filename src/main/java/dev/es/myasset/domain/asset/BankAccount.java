package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "bank_account")
public class BankAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long bankAcctId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(EnumType.STRING)
    private CompanyCode bankCode;

    private String acctNum;

    private BigDecimal balance;


}
