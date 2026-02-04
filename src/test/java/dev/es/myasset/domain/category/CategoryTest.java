package dev.es.myasset.domain.category;

import dev.es.myasset.domain.shared.ExpenseType;
import dev.es.myasset.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static dev.es.myasset.domain.category.Category.createCustomCategory;
import static dev.es.myasset.domain.category.Category.createDefaultCategory;
import static dev.es.myasset.domain.common.UserFixture.createUser;
import static org.assertj.core.api.Assertions.*;

class CategoryTest {

    @Test
    @DisplayName("카테고리 커스텀 생성 성공 테스트")
    void createCustomCategoryTest_success() {
        // given
        User user = createUser();
        String customCategoryName = "custom";

        // when then
        createCustomCategory(user, customCategoryName, ExpenseType.FIXED);
    }

    @Test
    @DisplayName("카테고리 커스텀 생성 실패 테스트 - 카테고리 명은 공백이 될 수 없다.")
    void createCustomCategoryTest_fail() {
        // given
        User user = createUser();
        String customCategoryName = "   ";

        // when then
        assertThatThrownBy(() ->
             createCustomCategory(user, customCategoryName, ExpenseType.FIXED)
        ).isInstanceOf(IllegalStateException.class);
    }

    @TestFactory
    @DisplayName("카테고리 생성 후 활성화/비활성화 시나리오")
    Collection<DynamicTest> customCategoryTests() {
        Category customCgy = createCustomCategory(createUser(), "custom", ExpenseType.FIXED);

        return List.of(

                DynamicTest.dynamicTest("생성 후 카테고리는 활성화 상태이다.", () ->
                    assertThat(customCgy.getIsActive()).isEqualTo(true)
                ),

                DynamicTest.dynamicTest("활성화 상태에서는 카테고리 활성화를 할 수 없다.", () ->
                     assertThatThrownBy(() ->
                             customCgy.activateCgv()
                     ).isInstanceOf(IllegalStateException.class)
                ),

                DynamicTest.dynamicTest("활성화 상태에서는 카테고리를 비활성화 할 수 있다.", () -> {
                    customCgy.deActivateCgv();

                    assertThat(customCgy.getIsActive()).isEqualTo(false);
                }),

                DynamicTest.dynamicTest("비활성화 상태에서는 카테고리를 비활성화 할 수 없다.", () -> {

                     assertThatThrownBy(() ->
                        customCgy.deActivateCgv()
                     ).isInstanceOf(IllegalStateException.class);

                }),

                DynamicTest.dynamicTest("비활성화 상태에서 카테고리를 활성화 할 수 있다.", () -> {
                    customCgy.activateCgv();
                    assertThat(customCgy.getIsActive()).isEqualTo(true);
                })
        );
    }

    @Test
    @DisplayName("디폴트 카테고리는 카테고리 명을 수정할 수 없다.")
    void changeCgyName_defaultCategory_fail() {
        // given
        Category defaultCategory = createDefaultCategory("default_category", ExpenseType.FIXED);

        // then when
        assertThatThrownBy(() ->
                defaultCategory.changeCgyName("edit")
        ).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("카테고리명 변경 시 공백이 들어갈 수 없다")
    void changeCgyName_blankName_fail() {
        // given
        Category customCgy = createCustomCategory(createUser(), "custom", ExpenseType.FIXED);
        String blankName = " ";

        // when then
        assertThatThrownBy(() ->
            customCgy.changeCgyName(blankName)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("커스텀 카테고리는 이름을 변경할 수 있다.")
    void changeCgyName_CgyName_success() {
        // given
        Category customCgy = createCustomCategory(createUser(), "custom", ExpenseType.FIXED);
        String editName = "otherName";

        // when then
        customCgy.changeCgyName(editName);
    }

}