package ma.enset.springangularmaterial.repositories;

import ma.enset.springangularmaterial.entities.Payment;
import ma.enset.springangularmaterial.entities.PaymentStatus;
import ma.enset.springangularmaterial.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);
}
