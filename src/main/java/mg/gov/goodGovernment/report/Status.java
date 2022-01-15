package mg.gov.goodGovernment.report;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
    NEW("new"),
    PROCESSING("processing"),
    DONE("done");

    private final String status;

    public static Boolean isValidStatus(String test) {
        for (Status status : Status.values()) {
            if (status.name().equals(test)) {
                return true;
            }
        }

        return false;
    }
}