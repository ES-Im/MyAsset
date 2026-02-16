package dev.es.myasset.domain.transaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static dev.es.myasset.domain.transaction.EXT.isAvailableExt;
import static org.assertj.core.api.Assertions.assertThat;

class EXTTest {

    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @ValueSource(strings = {"JPG", "PNG", "JPEG", "GIF", "WEBP", "jpg", "png", "jpeg", "gif", "webp"})
    @DisplayName("가능한 파일 확장자 테스트")
    void isAvailableExt_true(String ext) {
        // when then
        assertThat(isAvailableExt(ext)).isTrue();
    }

    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @ValueSource(strings = {"mp4", "7z", "zip", "exe"})
    @DisplayName("불가능한 파일 확장자 테스트")
    void isAvailableExt_false(String ext) {
        // when then
        assertThat(isAvailableExt(ext)).isFalse();
    }

}