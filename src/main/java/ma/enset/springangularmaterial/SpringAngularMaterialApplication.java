package ma.enset.springangularmaterial;

import ma.enset.springangularmaterial.entities.Payment;
import ma.enset.springangularmaterial.entities.PaymentStatus;
import ma.enset.springangularmaterial.entities.PaymentType;
import ma.enset.springangularmaterial.entities.Student;
import ma.enset.springangularmaterial.repositories.PaymentRepository;
import ma.enset.springangularmaterial.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class SpringAngularMaterialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAngularMaterialApplication.class, args);
    }



    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        PaymentRepository paymentRepository){
        return args -> {
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).
                    firstname("Mohammed")
                    .code("112133").
                    programId("SDIA").build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).
                    firstname("Imane")
                    .code("112233").
                    programId("GLSID").build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).
                    firstname("Yassmine")
                    .code("112333").
                    programId("SDIA").build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).
                    firstname("Nissy")
                    .code("112433").
                    programId("BDCC").build());

            PaymentType[] paymentTypes=PaymentType.values();
            Random random=new Random();
            studentRepository.findAll().forEach(st->{
                for (int i = 0; i < 10; i++) {
                    int index=random.nextInt(paymentTypes.length);
                    Payment payment=Payment.builder().
                    amount(1000+(int)(Math.random()+20000)).
                    type(paymentTypes[index]).date(LocalDate.now())
                            .status(PaymentStatus.VALIDATED)
                            .student(st).build();

                    paymentRepository.save(payment);
                }
            });
        };
    }
}
