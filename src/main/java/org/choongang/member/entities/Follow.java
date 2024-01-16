package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.entities.Base;

@Data
@Entity
public class Follow extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fromMember") //DB에 FK 변수명이 된다.
    private Member fromMember;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="toMember") //DB에 FK 변수명이 된다.
    private Member toMember;

}
