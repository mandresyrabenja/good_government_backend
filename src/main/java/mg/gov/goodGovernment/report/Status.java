package mg.gov.goodGovernment.report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    NEW("new"),
    PROCESSING("processing"),
    DONE("done");

    private final String status;

    public static Boolean isValidStatus(String test) {
        for (Status status : Status.values()) {
            if (status.status.equalsIgnoreCase(test)) {
                return true;
            }
        }

        return false;
    }
}