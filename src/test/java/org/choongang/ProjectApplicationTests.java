package org.choongang;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AuthoritiesRepository authoritiesRepository;

	@Test @Disabled  // 실행되지 않음
	void contextLoads() {
		Member member = memberRepository.findByUserId("user02").orElse(null);

		Authorities authorities = new Authorities();
		authorities.setMember(member);
		authorities.setAuthority(Authority.ADMIN);

		authoritiesRepository.saveAndFlush(authorities);
	}

}