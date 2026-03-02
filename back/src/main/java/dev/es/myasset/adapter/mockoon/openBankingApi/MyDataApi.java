package dev.es.myasset.adapter.mockoon.openBankingApi;

import dev.es.myasset.application.required.MyDataPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mydata")
public class MyDataApi {

    private final MyDataPort myDataPort;

    public MyDataApi(MyDataPort myDataPort) {
        this.myDataPort = myDataPort;
    }

    @GetMapping("/users")
    public String ping() {
        return myDataPort.ping();
    }


}