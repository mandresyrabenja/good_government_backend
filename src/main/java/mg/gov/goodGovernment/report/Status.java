package mg.gov.goodGovernment.report;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
    NEW("new"),
    PROCESSING("processing"),
    DONE("done");

    private final String permission;
}