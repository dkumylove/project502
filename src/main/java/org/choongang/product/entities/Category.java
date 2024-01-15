package org.choongang.product.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.BaseMember;
import org.springframework.data.annotation.Id;


@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(indexes = @Index(name="idx_category_listOrder", columnList = "listOrder DESC, createdAt DESC"))
public class Category extends BaseMember {
    @Id
    @Column(length=30)
    private String cateCd; // 분류코드

    @Column(length=60, nullable = false)
    private String cateNm; // 분류명

    private int listOrder; // 진열 가중치

    private boolean active; // 사용 여부
    @jakarta.persistence.Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}