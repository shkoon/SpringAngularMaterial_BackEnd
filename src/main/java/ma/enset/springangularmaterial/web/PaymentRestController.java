package ma.enset.springangularmaterial.web;


import lombok.AllArgsConstructor;
import ma.enset.springangularmaterial.dto.NewPaymentDTO;
import ma.enset.springangularmaterial.entities.Payment;
import ma.enset.springangularmaterial.entities.PaymentStatus;
import ma.enset.springangularmaterial.entities.PaymentType;
import ma.enset.springangularmaterial.entities.Student;
import ma.enset.springangularmaterial.repositories.PaymentRepository;
import ma.enset.springangularmaterial.repositories.StudentRepository;
import ma.enset.springangularmaterial.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class PaymentRestController {

    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    @GetMapping(path = "/payments")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public List<Payment> allPayments(){
       return paymentRepository.findAll();
    }
    @GetMapping(path = "/students/{code}/payments")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public List<Payment> getPaymentsByStudent(@PathVariable String code){
        return paymentRepository.findByStudentCode(code);
    }
    @GetMapping(path = "/payments/byStatus")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public List<Payment> getPaymentsByStatus(@RequestParam PaymentStatus paymentStatus){
        return paymentRepository.findByStatus(paymentStatus);
    }

    @GetMapping(path = "/payments/byType")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public List<Payment> getPaymentsByType(@RequestParam PaymentType paymentType){
        return paymentRepository.findByType(paymentType);
    }

    @GetMapping(path = "/payments/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public Payment getPaymentById(@PathVariable Long id){
        return paymentRepository.findById(id).get();
    }

    @GetMapping(path = "/students")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public List<Student> allStudents(){
        return studentRepository.findAll();
    }

    @GetMapping(path = "/students/{code}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
    }

    @GetMapping(path = "/studentsByProgramId")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public List<Student> getStudentsByProgramId(@RequestParam String programID){
        return studentRepository.findByProgramId(programID);
    }

    @PutMapping(path = "/payments/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus paymentStatus,@PathVariable Long id){

        return  paymentService.updatePaymentStatus(paymentStatus,id);
    }

    @PostMapping(path = "/payments",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public Payment savePayment(@RequestParam("file") MultipartFile file, NewPaymentDTO paymentDTO) throws IOException {

        return paymentService.savePayment(file,paymentDTO);

    }

    @GetMapping(path = "/paymentFile/{paymentId}",produces =MediaType.APPLICATION_PDF_VALUE )
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {

        Payment payment=paymentRepository.findById(paymentId).get();

        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}
