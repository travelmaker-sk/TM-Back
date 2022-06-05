package com.notice.board.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{
	
	public Account findByUsername(String userid);

	public boolean existsByUsername(String userid);

	public Account findByEmail(String email);

	public Account findByEmailCheckToken(String token);

	public boolean existsByEmail(String object);

	public void deleteByUsername(String str);
	
}
