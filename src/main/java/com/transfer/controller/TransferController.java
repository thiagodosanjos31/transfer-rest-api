package com.transfer.controller;

import com.transfer.dto.TransferDTO;
import com.transfer.service.TransferService;
import com.transfer.service.TransferValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    /**
     * 1. Buscar nome do cliente na API de Cadastro (Mock)
     * 2. Validar se a conta corrente está ativa;
     * Validar se o cliente tem limite disponível na Conta Corrente para realizar a
     * transferência;
     * 3. Validar se a transferência excedeu o limite diário de 1.000 reais.
     * 4. Após a transferência é necessário notificar o BACEN (mock) de forma síncrona que a
     * transação foi concluída com sucesso. A API do BACEN tem controle de rate limit e pode
     * retornar 429 em caso de chamadas que excedam o limite;
     * 5. Impedir que falhas momentâneas das dependências da aplicação impactem a
     * experiência do cliente;
     * 6. Ser desenvolvida em linguagem Java/Spring Boot;
     * 7. Apresentar testes unitários e automatizados;
     * 8. Explorar Design patterns;
     * 9. Implementar padrões de resiliência na aplicação.
     *
     */

    private final Logger logger = LoggerFactory.getLogger(TransferController.class);
    private final TransferService transferService;
    private final TransferValidationService transferValidationService;

    public TransferController(TransferService transferService, TransferValidationService transferValidationService) {
        this.transferService = transferService;
        this.transferValidationService = transferValidationService;
    }

    @PatchMapping("/")
    public ResponseEntity<String> transferBetweenAccounts(@RequestBody TransferDTO transferData) {
        logger.info("method=transferBetweenAccounts, message=Init transfer value between accounts.");
        transferValidationService.validation(transferData);
        transferService.transfer(transferData);
        return ResponseEntity.status(HttpStatus.OK).body("Transfer completed successfully");
    }
            

}
