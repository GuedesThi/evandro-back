package br.com.decasa.api.entities;

import br.com.decasa.api.enums.RoleName;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "cargos")
public class UserRole implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCargo;

    @Enumerated(EnumType.STRING)
    @Column(name = "cargo", nullable = false, unique = true)
    private RoleName role;

    @Override
    public String getAuthority() {
        return role.toString();
    }

    public UUID getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(UUID idCargo) {
        this.idCargo = idCargo;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }
}