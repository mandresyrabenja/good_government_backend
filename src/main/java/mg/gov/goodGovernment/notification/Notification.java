package mg.gov.goodGovernment.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.region.Region;
import mg.gov.goodGovernment.report.Report;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Notification {
    @Id
    private Long id;
    private Report report;
    private Citizen citizen;
    private Region region;
    @Transient
    public static final String SEQUENCE_NAME = "report_sequence";
}