package com.mutsa.delivery.user.repository;

import com.mutsa.delivery.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
