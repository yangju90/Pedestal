package indi.mat.work.monitor;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

@Endpoint(
    id = "faq"
)
public class FaqEndpoint {
    public FaqEndpoint() {
    }

    @ReadOperation
    public String faq() {
        return "OK";
    }
}
