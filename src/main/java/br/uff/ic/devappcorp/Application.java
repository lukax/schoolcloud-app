package br.uff.ic.devappcorp;

import br.uff.ic.devappcorp.entities.*;
import br.uff.ic.devappcorp.repositories.StudentRepository;
import br.uff.ic.devappcorp.utils.Result;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(StudentRepository studentRepository) {
		return (evt) -> Arrays.asList(
				"Sandor Clegane,Thoros of Myr,Ser Jorah Mormont,Tormund Giantsbane,John Snow".split(","))
				.forEach(
						a -> {
							String taxNumber = String.valueOf(Math.round(Math.random() * 1_000_000_000_00L));
							Result<PersonTaxNumber> personTaxNumber = PersonTaxNumber.create(taxNumber);
							Result<PersonName> personName = PersonName.create(a);
							Result<EmailAddress> email = EmailAddress.create(a.replace(" ", ".").toLowerCase() + "@gmail.com");

							PersonDetail personDetail = new PersonDetail(personTaxNumber.value(), personName.value(), email.value());
							Student student = new Student(personDetail);

							studentRepository.save(student);
						});
	}
}