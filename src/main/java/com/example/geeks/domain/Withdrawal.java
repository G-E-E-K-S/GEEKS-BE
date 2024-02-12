package com.example.geeks.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "withdrawal_id")
    private Long id;

    private String reason;

    private String detailReason;

    public Withdrawal(String reason, String detailReason) {
        this.reason = reason;
        this.detailReason = detailReason;
    }
}
