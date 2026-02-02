package dev.es.myasset.domain.category;

import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.ExpenseType;
import dev.es.myasset.domain.user.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key", nullable = false)
    private User user;  // default = 시스템 디폴트 설정

    @Column(nullable = false)
    private String cgyName;

    @Enumerated(STRING)
    @Column(nullable = false)
    private ExpenseType expenseType;

    @Column(nullable = false)
    private Boolean isActive;


    public static Category createCategory(User user, String cgyName, ExpenseType expenseType) {
        Category category = new Category();

        state(!cgyName.trim().isEmpty(), "카테고리명은 공백이 될 수 없음");

        category.user = requireNonNull(user);
        category.cgyName = requireNonNull(cgyName);
        category.expenseType = requireNonNull(expenseType);
        category.isActive = true;

        return category;
    }

    public void deActivateCgv() {
        this.isActive = false;
    }

    public void activateCgv() {
        this.isActive = true;
    }

    public void changeCgyName(String newCgyName) {
        state(!newCgyName.trim().isEmpty(), "카테고리명은 공백이 될 수 없음");

        this.cgyName = requireNonNull(newCgyName);
    }

}
