package com.github.sbadolankaur.rpg.identity.persistence;

import com.github.sbadolankaur.rpg.identity.domain.InstanceState;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstanceStateRepository extends JpaRepository<InstanceState, Short> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select state from InstanceState state where state.id = :id")
    Optional<InstanceState> lockById(@Param("id") Short id);
}
