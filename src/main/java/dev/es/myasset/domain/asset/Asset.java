package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Getter
@Table(name = "asset")
public class Asset extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key")
    private User user;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    @OneToMany(mappedBy = "asset", cascade = {PERSIST, REMOVE})
    private List<StockAccount> stockAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "asset", cascade = {PERSIST, REMOVE})
    private List<Card> cards = new ArrayList<>();

    @OneToMany(mappedBy = "asset", cascade = {PERSIST, REMOVE})
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToOne(mappedBy = "asset", cascade = {PERSIST, REMOVE})
    private Cash cash;
}
