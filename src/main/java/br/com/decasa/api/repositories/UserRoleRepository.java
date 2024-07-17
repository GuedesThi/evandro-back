package br.com.decasa.api.repositories;

import br.com.decasa.api.entities.UserRole;
import br.com.decasa.api.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    Optional<UserRole> findByRole(RoleName role);
}