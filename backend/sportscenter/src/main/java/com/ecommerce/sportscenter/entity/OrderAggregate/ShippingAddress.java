package com.ecommerce.sportscenter.entity.OrderAggregate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representa o endereço de entrega embutido nos detalhes do pedido")
public class ShippingAddress {

    @NotBlank(message = "O nome do destinatário é obrigatório")
    @Size(max = 100, message = "O nome do destinatário não pode exceder 100 caracteres")
    @Column(name = "shipping_name", length = 100)
    @Schema(description = "Nome completo do destinatário da entrega", example = "Gilson Ramos")
    private String name;

    @NotBlank(message = "O endereço principal (rua, número) é obrigatório")
    @Size(max = 150, message = "O endereço não pode exceder 150 caracteres")
    @Column(name = "shipping_address1", length = 150)
    @Schema(description = "Logradouro, número e bairro", example = "Rua XV de Novembro, 1234 - Centro")
    private String address1;

    @Size(max = 100, message = "O complemento não pode exceder 100 caracteres")
    @Column(name = "shipping_address2", length = 100)
    @Schema(description = "Complemento opcional (Apartamento, Bloco, etc.)", example = "Apto 42 Bloco B")
    private String address2;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(max = 100, message = "A cidade não pode exceder 100 caracteres")
    @Column(name = "shipping_city", length = 100)
    @Schema(description = "Cidade de destino", example = "Curitiba")
    private String city;

    @NotBlank(message = "O estado/província é obrigatório")
    @Size(min = 2, max = 50, message = "O estado deve ter entre 2 e 50 caracteres")
    @Column(name = "shipping_state", length = 50)
    @Schema(description = "Estado ou região", example = "Paraná")
    private String state;

    @NotBlank(message = "O CEP/Código Postal é obrigatório")
    @Size(min = 5, max = 20, message = "O CEP/Zipcode deve ter entre 5 e 20 caracteres")
    @Column(name = "shipping_zipcode", length = 20)
    @Schema(description = "Código postal ou CEP de entrega", example = "80020-000")
    private String zipcode;

    @NotBlank(message = "O país é obrigatório")
    @Size(max = 50, message = "O país não pode exceder 50 caracteres")
    @Column(name = "shipping_country", length = 50)
    @Schema(description = "País de destino", example = "Brasil")
    private String country;
}