package dev.es.myasset.domain.category;

import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.ExpenseType;
import dev.es.myasset.domain.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_key", "cgy_name"}),
    name = "category"
)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cgyId;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key", nullable = true)
    private User user;  // default = 시스템 디폴트 설정

    @Column(nullable = false)
    private String cgyName;

    @Enumerated(STRING)
    @Column(nullable = false)
    private ExpenseType expenseType;

    @Column(nullable = false)
    private Boolean isActive;


    public static Category createCustomCategory(User user, String cgyName, ExpenseType expenseType) {
        Category category = createDefaultCategory(cgyName, expenseType);
        category.user = requireNonNull(user);

        return category;
    }

    static Category createDefaultCategory(String cgyName, ExpenseType expenseType) {
        Category category = new Category();

        checkBlank(cgyName);

        category.cgyName = requireNonNull(cgyName);
        category.expenseType = requireNonNull(expenseType);
        category.isActive = true;

        return category;
    }

    public void deActivateCgv() {
        state(this.isActive, "이미 비활성화된 카테고리입니다.");

        this.isActive = false;
    }

    public void activateCgv() {
        state(!this.isActive, "이미 활성화된 카테고리입니다.");

        this.isActive = true;
    }

    public void changeCgyName(String newCgyName) {
        requireNonNull(this.user);
        checkBlank(newCgyName);

        this.cgyName = requireNonNull(newCgyName);
    }

    private static void checkBlank(String newCgyName) {
        state(!newCgyName.trim().isEmpty(), "카테고리명은 공백이 될 수 없음");
    }

}
