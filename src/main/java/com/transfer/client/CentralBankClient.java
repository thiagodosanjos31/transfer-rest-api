package com.transfer.client;

import com.transfer.dto.CentralBankTransfer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "central-bank", url = "http://localhost:8081/v1/")
public interface CentralBankClient {

    @RequestMapping(method = RequestMethod.POST, value = "/transfers")
    void sendInformationForCentralBank(@RequestBody CentralBankTransfer centralBankTransfer);

}
