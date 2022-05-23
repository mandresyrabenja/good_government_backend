package mg.gov.goodGovernment.http;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Classe du reponse HTTP
 *
 * @author Mandresy
 */
@Data
@AllArgsConstructor
public class HttpResponse {
    private LocalDateTime time;
    // Status HTTP
    private Integer status;
    private Boolean error;
    private String msg;

    public HttpResponse(Integer status, Boolean error, String msg) {
        this.status = status;
        this.error = error;
        this.msg = msg;
        this.time = LocalDateTime.now();
    }
}
