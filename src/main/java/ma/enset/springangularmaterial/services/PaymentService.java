package ma.enset.springangularmaterial.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.enset.springangularmaterial.dto.NewPaymentDTO;
import ma.enset.springangularmaterial.entities.Payment;
import ma.enset.springangularmaterial.entities.PaymentStatus;
import ma.enset.springangularmaterial.entities.PaymentType;
import ma.enset.springangularmaterial.repositories.PaymentRepository;
import ma.enset.springangularmaterial.repositories.StudentRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;


    public Payment savePayment(MultipartFile file, NewPaymentDTO paymentDTO) throws IOException {

        Path folderPath= Paths.get(System.getProperty("user.home"),"enset-data","payments");

        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }

        String fileName= UUID.randomUUID().toString();
        Path filePath= Paths.get(System.getProperty("user.home"),"enset-data","payments",fileName+".pdf");

        Files.copy(file.getInputStream(),filePath);

        Payment payment=Payment.builder().amount(paymentDTO.getAmount())
                .date(paymentDTO.getDate()).type(paymentDTO.getPaymentType()).student(studentRepository.findByCode(paymentDTO.getStudentCode()))
                .file(filePath.toUri().toString())
                .status(PaymentStatus.CREATED)
                .build();

        return paymentRepository.save(payment);

    }


    public Payment updatePaymentStatus( PaymentStatus paymentStatus, Long id){
        Payment payment=paymentRepository.findById(id).get();
        payment.setStatus(paymentStatus);
        return  paymentRepository.save(payment);
    }
}
