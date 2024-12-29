package com.erp.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.backend.model.UserModel;


@Repository
public interface UserRepository extends JpaRepository<UserModel,Long>{
  public Optional<UserModel> findByUserId(Long userId);
}
