package org.example.vrback.repository;

import org.example.vrback.model.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {
    @Query("SELECT EXISTS(SELECT token FROM tokens WHERE token = :token AND activation_count > 0)")
    boolean existsByToken(int token);

    @Modifying
    @Query("UPDATE tokens SET activation_count = activation_count - 1 WHERE token = :token")
    void decrementActivationCount(int token);

    List<Token> findAll();
 }
