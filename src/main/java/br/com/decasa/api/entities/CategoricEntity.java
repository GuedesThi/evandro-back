package br.com.decasa.api.entities;

import br.com.decasa.api.enums.Categorics;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categorias")
public class CategoricEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCategoric;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Categorics categoric;

    @OneToMany(mappedBy = "categoric")
    @JsonManagedReference
    private List<ProductEntity> products;

    public UUID getIdCategoric() {
        return idCategoric;
    }

    public void setIdCategoric(UUID idCategoric) {
        this.idCategoric = idCategoric;
    }

    public Categorics getCategoric() {
        return categoric;
    }

    public void setCategoric(Categorics categoric) {
        this.categoric = categoric;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }
}