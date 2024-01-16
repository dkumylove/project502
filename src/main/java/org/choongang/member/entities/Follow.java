package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.entities.Base;

@Data
@Entity
public class Follow extends Base {

    @Id
    @GeneratedValue
    private Long seq;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="follower")
    private Member follower;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="following")
    private Member following;

}
