package com.scoio.book.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Tokens, Integer> {
    Optional<Tokens> findByToken(String token);
}
