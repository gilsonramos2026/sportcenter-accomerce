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
@Table(name = "tb_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representa a categoria ou tipo de produto esportivo (ex: Calçados, Roupas)")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "ID único do tipo gerado automaticamente pelo banco", example = "1")
    private Integer id;

    @NotBlank(message = "O nome do tipo/categoria é obrigatório")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    @Column(name = "name", nullable = false, length = 50)
    @Schema(description = "Nome descritivo da categoria", example = "Calçados")
    private String name;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore // Evita recursão infinita ao listar os dados da categoria via API
    @Schema(description = "Lista de produtos associados a esta categoria")
    private List<Product> products;
}
