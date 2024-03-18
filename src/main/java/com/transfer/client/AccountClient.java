package com.transfer.client;

import com.transfer.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "account", url = "http://localhost:8081/v1/")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.GET, value = "/accounts")
    List<AccountDTO> getAccounts(@RequestParam("ids") String ids);

}
