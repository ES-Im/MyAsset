package dev.es.myasset.domain.transaction;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.requireNonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "transaction_file")
public class TransactionFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tran_id", nullable = false)
    private Transactions transaction;

    private String sourceFileName;

    private String destinationFileName;

    @Enumerated(STRING)
    private EXT ext;

    private Integer size;

    @Builder
    public TransactionFile(
            Transactions transaction
            , String sourceFileName
            , EXT ext, Integer size) {

        this.transaction = requireNonNull(transaction);
        this.sourceFileName = requireNonNull(sourceFileName);
        this.destinationFileName = UUID.randomUUID().toString();
        this.ext = requireNonNull(ext);
        this.size = requireNonNull(size);

    }

}
