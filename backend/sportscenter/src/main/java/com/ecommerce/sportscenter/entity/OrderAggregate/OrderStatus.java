package com.ecommerce.sportscenter.entity.OrderAggregate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Define os estados possíveis do ciclo de vida de um pedido")
public enum OrderStatus {
    
    @Schema(description = "Pedido criado, aguardando a confirmação do pagamento")
    Pending,
    
    @Schema(description = "Pagamento confirmado com sucesso, pedido pronto para processamento")
    PaymentReceived,
    
    @Schema(description = "Tentativa de pagamento recusada ou falhou")
    PaymentFailed
}
