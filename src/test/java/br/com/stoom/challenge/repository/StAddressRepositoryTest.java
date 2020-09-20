package br.com.stoom.challenge.repository;

import br.com.stoom.challenge.entity.StAddress;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class StAddressRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private StAddressRepository stAddressRepository;

    @Test
    public void shouldSaveAddress() {
        var expected = stAddressRepository.save(StAddress.builder()
            .city("City")
            .complement("Complement")
            .country("Country")
            .latitude("123")
            .longitude("123")
            .neighbourhood("neighbourhood")
            .number("123")
            .state("asdasd")
            .streetName("street")
            .zipcode("123")
            .build());

        var actual = testEntityManager.find(StAddress.class, expected.getId());
        Assertions.assertThat(actual)
            .isEqualTo(expected);
    }

    @Test
    public void shouldFindById() {
        var expected = testEntityManager.persistAndFlush(StAddress.builder()
            .city("City")
            .complement("Complement")
            .country("Country")
            .latitude("123")
            .longitude("123")
            .neighbourhood("neighbourhood")
            .number("123")
            .state("asdasd")
            .streetName("street")
            .zipcode("123")
            .build());

        var actual = stAddressRepository.findById(expected.getId()).orElseThrow();
        Assertions.assertThat(actual)
            .isEqualTo(expected);
    }
}