package dev.es.myasset.domain.transaction;

import dev.es.myasset.domain.shared.BaseEntity;
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
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"destination_file_name", "tran_id"}),
        name = "transaction_file"
)
public class TransactionFile extends BaseEntity {

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
