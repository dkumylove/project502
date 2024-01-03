package org.choongang.board.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.choongang.commons.entities.BeseMember;

@Data
@Entity
public class BoardData extends BeseMember {

    @Id  @GeneratedValue
    private Long seq;

    private String subject;
    private String content;
}
