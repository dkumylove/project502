package org.choongang;

import org.choongang.member.Authority;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.AuthoritiesRepository;
import org.choongang.member.repositories.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AuthoritiesRepository authoritiesRepository;


	/**
	 * 관리자 권한부여
	 */
	@Test //@Disabled  // 실행되지 않음
	void contextLoads() {
		Member member = memberRepository.findByUserId("user02").orElse(null);

		Authorities authorities = new Authorities();
		authorities.setMember(member);
		authorities.setAuthority(Authority.ADMIN);

		authoritiesRepository.saveAndFlush(authorities);
	}

}