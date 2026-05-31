package com.ecommerce.sportscenter.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_basket")
@Schema(description = "Representa o carrinho de compras do usuário")
public class Basket {

    @Id
    @NotBlank(message = "O ID do carrinho não pode estar em branco")
    @Schema(description = "Identificador único do carrinho (ex: e-mail ou UUID)", example = "cliente@email.com")
    private String id;

    @Valid
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de itens adicionados ao carrinho")
    private List<BasketItem> items = new ArrayList<>();

    public Basket(String id) {
        this.id = id;
    }

    // Helper method para garantir a consistência do relacionamento bidirecional
    public void addItem(BasketItem item) {
        items.add(item);
        item.setBasket(this);
    }

    public void removeItem(BasketItem item) {
        items.remove(item);
        item.setBasket(null);
    }
}
