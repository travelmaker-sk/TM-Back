package com.photocard.demo.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{
	
	public Account findByUsername(String userid);

	public boolean existsByUsername(String userid);
	
}
