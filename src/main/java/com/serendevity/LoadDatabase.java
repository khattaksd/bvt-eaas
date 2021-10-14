package com.serendevity;

import com.github.javafaker.Business;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class LoadDatabase implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private CardRepository cardRepo;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        for (int i = 0; i < 100; i++) {
            final Name n = faker.name();
            final Person p = new Person(n.firstName(), faker.name().lastName());
            log.info("Preloading " + personRepo.save(p));
            final Business b = faker.business();
            final Card c = new Card(p, b.creditCardNumber(), b.creditCardExpiry(), faker.number().digits(3));
            log.info("Preloading " + cardRepo.save(c));
        }
    }
}
