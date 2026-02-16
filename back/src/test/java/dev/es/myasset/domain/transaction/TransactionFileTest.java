package dev.es.myasset.domain.transaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.regex.Pattern;

import static dev.es.myasset.domain.common.TransactionsFixture.cardTransaction;
import static dev.es.myasset.domain.transaction.EXT.*;
import static dev.es.myasset.domain.transaction.TransactionFile.createTransactionFile;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class TransactionFileTest {

    private long parsingMB(int mb) {
        return (long) mb * 1024 * 1024;
    }

    @Test
    @DisplayName("거래내역 첨부파일 객체 생성 성공 테스트")
    void createTransactionFile_success() {
        // given
        Transactions tran = cardTransaction();
        String sourceFileName = "sourcefile.png";
        long fileSize = parsingMB(1);
        // when
        TransactionFile tranFile = createTransactionFile(tran, sourceFileName, fileSize);

        // then
        assertThat(tranFile)
                .extracting("sourceFileName", "ext")
                .containsExactly("sourcefile", PNG);

        assertThat(tranFile.getDestinationFileName()).isNotNull();

    }

    @ParameterizedTest(name = "{index} ==> ''{1}''일 때 파일첨부 객체 생성을 할 수 없다.")
    @CsvSource({
            "0, 파일사이즈가 0MB",
            "10000, 파일 사이즈가 기준보다 클때"
    })
    @DisplayName("거래첨부파일 객체 생성 실패 테스트")
    void createTransactionFile_fail1(int givenSize, String description) {
        // given
        Transactions tran = cardTransaction();
        String sourceFileName = "sourcefile.png";
        long fileSize = parsingMB(givenSize);

        // when then
        assertThatThrownBy(() ->
                createTransactionFile(tran, sourceFileName, fileSize)
        ).isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest(name = "{index} ==> ''{1}''문제 오류시 파일첨부 객체 생성을 할 수 없다.")
    @CsvSource({
            "filename, 확장자명",
            "'  ', 공백"
    })
    @DisplayName("거래첨부파일 객체 생성 실패 테스트")
    void createTransactionFile_fail2(String givenSourceFileName, String description) {
        Transactions tran = cardTransaction();
        String sourceFileName = givenSourceFileName;
        long fileSize = parsingMB(1);

        // when then
        assertThatThrownBy(() ->
                createTransactionFile(tran, sourceFileName, fileSize)
        ).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(description);

    }

    @Test
    @DisplayName("서버 저장 파일명은 UUID이며, 고정된 형식(파일명.확장자) 형식을 만족한다.")
    void getStoredFileNameTest() {
        // given
        Transactions tran = cardTransaction();
        String sourceFileName = "sourcefile@.png";
        long fileSize = parsingMB(1);

        // when
        Pattern pattern = Pattern.compile("^[^.]+\\.[a-zA-Z0-9]+$");
        TransactionFile tranFile = createTransactionFile(tran, sourceFileName, fileSize);

        // then
        assertThat(tranFile.getStoredFileName())
                .doesNotContain(sourceFileName)
                .matches(pattern);
    }

    @Test
    @DisplayName("다운로드 파일명은 원본파일이름이며, 고정된 형식(파일명.확장자) 형식을 만족한다.")
    void getDownloadFileNameTest() {
        // given
        Transactions tran = cardTransaction();
        String sourceFileName = "sourcefile.png";
        long fileSize = parsingMB(1);
        // when
        Pattern pattern = Pattern.compile("^[^.]+\\.[a-zA-Z0-9]+$");
        TransactionFile tranFile = createTransactionFile(tran, sourceFileName, fileSize);

        // then
        assertThat(tranFile.getDownloadFileName())
                .matches(pattern)
                .contains(sourceFileName.substring(0, sourceFileName.lastIndexOf('.')));
    }

}