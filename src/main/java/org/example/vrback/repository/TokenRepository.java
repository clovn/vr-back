package org.example.vrback.repository;

import org.example.vrback.model.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {
    boolean existsByToken(int token);
}
