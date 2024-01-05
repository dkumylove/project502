package org.choongang.file.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.BaseMember;

import java.util.List;
import java.util.UUID;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_fInfo_gid", columnList = "gid"),
        @Index(name = "idx_fInfo_gid_loc", columnList = "gid,location")
})  // 자주 검색하는것은 인덱스화 하는 것이 좋음
public class FileInfo extends BaseMember {

    /**
     * 내가 올린 파일만 삭제하기 위해 누가 작성했는지 기억해야함.
     * BaseMember의 createdBy 사용
     * (java,util)
     * UUID.randomUUID() 유니크 아이디를 랜덤으로 만들수 있음
     */
    @Id @GeneratedValue
    private Long seq;  // 파일 등록 번홉, 서버에 업로드하는 파일명 기준

    @Column(length = 65, nullable = false)
    private String gid = UUID.randomUUID().toString();  // 게시글 하나의 여러개 파일

    @Column(length = 65)
    private String location;  // 이미지 출처

    @Column(length = 80)  // 한글 3바이트 100자면 33자 정도 입력가능
    private String fileName;  // 원래파일명, 오리지널 파일이름

    @Column(length = 30)
    private String extension;  // 확장자

    @Column(length = 65)
    private String fileType;  // 파일 타입

    @Transient  // 내부에서만 사용
    private String filePath;  // 서버에 실제 올라간 경로

    @Transient  // 내부에서만 사용
    private String fileUrl;  // 브라우저 주소창에 입력해서 접근 할 수 있는 경로

    @Transient  // 내부에서만 사용
    private List<String> thumbsPath;  // 썸네일 이미지 경로

    @Transient  // 내부에서만 사용
    private List<String> thumbsUrl;   // 브라우저 주소창에 입력해서 접근할 수 있는 경로

    private boolean done;  // 작업 완료 유무

}
