package com.serendevity;

import com.github.javafaker.Business;
import com.github.javafaker.Faker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PersonRepository personRepo, CardRepository cardRepo) {
        Faker faker = new Faker();

        return args -> {
            for (int i = 0; i < 10; i++) {
                final Person p = new Person(faker.name().firstName(), faker.name().lastName());
                log.info("Preloading " + personRepo.save(p));
                Business b = faker.business();
                final Card c = new Card(p, b.creditCardNumber(), b.creditCardExpiry(), faker.number().digits(3));
                log.info("Preloading " + cardRepo.save(c));
            }
        };
    }
}
