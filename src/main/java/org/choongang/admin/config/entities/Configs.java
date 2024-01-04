package org.choongang.admin.config.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity
public class Configs {

    @Id
    @Column(length = 60)
    private  String code;

    @Lob  // 데이터는 JSON방식으로
    private String data;
}
