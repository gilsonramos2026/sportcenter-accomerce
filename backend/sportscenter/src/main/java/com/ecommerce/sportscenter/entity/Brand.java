package com.ecommerce.sportscenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representa uma marca de produtos esportivos no catálogo")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "ID único da marca gerado automaticamente pelo banco", example = "1")
    private Integer id;

    @NotBlank(message = "O nome da marca é obrigatório")
    @Size(min = 2, max = 50, message = "O nome da marca deve ter entre 2 e 50 caracteres")
    @Column(name = "name", nullable = false, length = 50)
    @Schema(description = "Nome comercial da marca", example = "Nike")
    private String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore // Impede loops de recursão infinita ao listar os produtos da marca via API
    @Schema(description = "Lista de produtos que pertencem a esta marca")
    private List<Product> products;
}
