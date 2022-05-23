package mg.gov.goodGovernment.report;

import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.citizen.CitizenRepository;
import mg.gov.goodGovernment.region.Region;
import mg.gov.goodGovernment.region.RegionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReportRepositoryTest {

    @Autowired
    ReportRepository underTest;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CitizenRepository citizenRepository;
    Report report;
    Citizen citizen;
    Region region;

    @BeforeEach
    void setUp() {
        report = new Report();
        region = new Region("nomRegion", "bar");
        regionRepository.save(region);
        citizen = new Citizen(
                123_456_789L,
                "Mandresy",
                "Rabenja",
                LocalDate.of(2001, Month.MARCH, 8),
                "mandresyrabenj@gmail.com",
                "abcd1234");
        citizenRepository.save(citizen);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        citizenRepository.deleteAll();
        regionRepository.deleteAll();
    }

    @Test
    void itShouldSearchRegionReportsUsingKeywords() {
        //given
        report = new Report(
                citizen,
                LocalDate.now(),
                "Lorem ipsum",
                "bar",
                1.0,
                1.0,
                region,
                Status.NEW.getStatus()
        );
        underTest.save(report);
        region = regionRepository.findByNameIgnoreCase(region.getName())
                    .orElseThrow(() -> new IllegalStateException("Aucun region trouvé"));

        //when
        List<Report> actual = underTest.searchRegionReport(region.getId(), "ipsum");

        //then
        assertThat(actual).isNotEmpty()
            .contains(report);
    }

    @Test
    void itShouldFindReportUsingRegion() {
        //given
        report = new Report(
                citizen,
                LocalDate.now(),
                "foo",
                "bar",
                1.0,
                1.0,
                region,
                Status.NEW.getStatus()
        );
        underTest.save(report);
        Pageable page = PageRequest.of(0, 10);

        //when
        List<Report> actual = underTest.findByRegion(region, page);

        //then
        assertThat(actual).isNotEmpty()
                .contains(report);
    }

    @Test
    void itShouldReportsThatHaveNoRegionYet() {
        // given
        report = new Report(
                citizen,
                LocalDate.now(),
                "foo",
                "bar",
                1.0,
                1.0,
                null,
                Status.NEW.getStatus()
        );
        underTest.save(report);
        Pageable page = PageRequest.of(0, 10);

        //when
        List<Report> actual = underTest.findByRegionIsNull(page);

        //then
        assertThat(actual).isNotEmpty()
                .contains(report);
    }

    @Test
    void itShouldFindReportUsingCitizen() {
        //given
        report = new Report(
                citizen,
                LocalDate.now(),
                "foo",
                "bar",
                1.0,
                1.0,
                region,
                Status.NEW.getStatus()
        );
        underTest.save(report);
        Pageable page = PageRequest.of(0, 10);

        //when
        List<Report> actual = underTest.findByCitizen(citizen, page);

        //then
        assertThat(actual).isNotEmpty()
                .contains(report);
    }
}