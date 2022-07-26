package com.Huduks.UYAuth.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Huduks.UYAuth.DTO.UserProfile;

@Repository
public interface AuthRepository extends CrudRepository<UserProfile, Long>{
	
	public Optional<UserProfile> findUserProfileByEmail(String email);

}
