package com.scoio.book.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Integer> {

    Optional<Roles> findByName(String role);
}
