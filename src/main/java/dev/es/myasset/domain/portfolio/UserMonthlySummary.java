package dev.es.myasset.domain.portfolio;

import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="user_monthly_summary")
public class UserMonthlySummary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long summaryId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_key", nullable = false)
    private User user;

    private Integer year;

    private Integer month;

    private BigDecimal fixedSpent;

    private BigDecimal variableSpent;

    private BigDecimal avgDailyVariableSpent;

    private BigDecimal totalSpent;

}
