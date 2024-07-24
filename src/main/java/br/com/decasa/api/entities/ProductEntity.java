package br.com.decasa.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "produtos")
public class ProductEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProduct;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "categoric_id", nullable = false)
    @JsonBackReference
    private CategoricEntity categoric;

    @Column(name = "codigoDoProduto", unique = true)
    private String productCode;

    @Column(name = "preco", nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer parcelas;

    @Column(nullable = false)
    private Integer emEstoque;

    @Column(name = "imagensUrl")
    private List<String> imagesUrl;

    public UUID getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoricEntity getCategoric() {
        return categoric;
    }

    public void setCategoric(CategoricEntity categoric) {
        this.categoric = categoric;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public Integer getEmEstoque() {
        return emEstoque;
    }

    public void setEmEstoque(Integer emEstoque) {
        this.emEstoque = emEstoque;
    }

    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
