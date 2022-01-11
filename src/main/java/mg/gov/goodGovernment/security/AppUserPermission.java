package mg.gov.goodGovernment.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Liste des permissions
 */
@RequiredArgsConstructor
@Getter
public enum AppUserPermission {
    CITIZEN_CREATE("citizen:create"),
    CITIZEN_READ("citizen:read"),
    CITIZEN_UPDATE("citizen:update"),
    CITIZEN_DELETE("citizen:delete"),

    REGION_CREATE("region:create"),
    REGION_READ("region:read"),
    REGION_UPDATE("region:update"),
    REGION_DELETE("region:delete"),

    REPORT_CREATE("report:create"),
    REPORT_READ("report:read"),
    REPORT_UPDATE("report:update"),
    REPORT_DELETE("report:delete"),

    NOTIFICATION_CREATE("notification:create"),
    NOTIFICATION_READ("notification:read"),
    NOTIFICATION_UPDATE("notification:update"),
    NOTIFICATION_DELETE("notification:delete");

    private final String permission;
}