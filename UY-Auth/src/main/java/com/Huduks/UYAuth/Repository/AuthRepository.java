package com.Huduks.UYAuth.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Huduks.UYAuth.DTO.UserProfile;

@Repository
public interface AuthRepository extends CrudRepository<UserProfile, Long>{
	
	public UserProfile findUserProfileByEmail(String email);

}
