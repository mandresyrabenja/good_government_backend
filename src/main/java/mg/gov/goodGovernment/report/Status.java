package mg.gov.goodGovernment.report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Les status de signalement de problème.<br>
 * <ul>
 *     <li><b>NEW</b> nouvel signalement de problème</li>
 *     <li><b>PROCESSING</b> problème en cours de traitement</li>
 *     <li><b>DONE</b> problème resolu</li>
 * </ul>
 */
@Getter
@RequiredArgsConstructor
public enum Status {
    NEW("new"),
    PROCESSING("processing"),
    DONE("done");

    private final String status;

    public static Boolean isValidStatus(String test) {
        return Arrays.stream(Status.values())
                .anyMatch(s -> s.getStatus().equalsIgnoreCase(test));
    }
}