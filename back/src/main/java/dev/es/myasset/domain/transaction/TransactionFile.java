package dev.es.myasset.domain.transaction;

import dev.es.myasset.domain.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"destination_file_name", "tran_id"}),
        name = "transaction_file"
)
public class TransactionFile extends BaseEntity {

    private static final long MAX_FILE_SIZE = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tran_id", nullable = false)
    private Transactions transaction;

    @Column(nullable = false)
    private String sourceFileName;

    @Column(nullable = false)
    private String destinationFileName; // UUID

    @Enumerated(STRING)
    @Column(nullable = false)
    private EXT ext;

    @Column(nullable = false)
    private Integer size;   // byte

    public static TransactionFile createTransactionFile(
            Transactions transaction, String sourceFileName, long size
    ) {
        String[] splitName = splitSourceFileName(sourceFileName);
        validateSize(size);

        TransactionFile tranFile = new TransactionFile();

        tranFile.transaction = requireNonNull(transaction);
        tranFile.sourceFileName = splitName[0];
        tranFile.destinationFileName = UUID.randomUUID().toString();
        tranFile.ext = EXT.valueOf(splitName[1].toUpperCase());
        tranFile.size = (int) size;

        return tranFile;
    }

    private static void validateSize(long size) {
        state(size != 0, "파일 크기는 0보다 커야합니다.");
        state(size <= MAX_FILE_SIZE*1024*1024, "파일 크기는" + MAX_FILE_SIZE + "MB 이하여야 합니다.");
    }

    private static String[] splitSourceFileName(String sourceFileName) {
        state(!sourceFileName.isBlank(), "원본 파일 명은 공백이 들어올 수 없습니다");
        state(sourceFileName.contains("."),"확장자명이 없는 파일입니다.");

        int spotLen = sourceFileName.lastIndexOf(".");

        return new String[] {
                sourceFileName.substring(0, spotLen),
                sourceFileName.substring(spotLen + 1)
        };
    }

    public String getStoredFileName() {
        return this.destinationFileName + "." + this.ext.name();
    }

    public String getDownloadFileName() {
        return this.sourceFileName + "." + this.ext.name();
    }

}
