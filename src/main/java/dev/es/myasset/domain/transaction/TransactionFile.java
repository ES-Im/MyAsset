package dev.es.myasset.domain.transaction;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

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


}
