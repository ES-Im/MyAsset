package dev.es.myasset.domain.category;

import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "category")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cgv_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key", nullable = true)
    private User user;  // null 시 cgy 타입 = default 타입

    private String cgyType; // default 타입 : 고정비/식비/교통비·차량/생활비/의료비/자기계발비/여가비

    @Enumerated(STRING)
    private ExpenseType expenseType;

    private Boolean isDefault;

    private Boolean isActive;

}
