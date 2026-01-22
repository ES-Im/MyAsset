package dev.es.myasset.domain.portfolio;

import dev.es.myasset.domain.category.ExpenseType;
import dev.es.myasset.domain.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="user_monthly_detail")
public class UserMonthlyDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long detailId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="summary_id", nullable = false)
    private UserMonthlySummary userMonthlySummary;

    @Enumerated(STRING)
    private ExpenseType expenseType;

    private BigDecimal avgSpent;

    private BigDecimal totalSpent;
}
