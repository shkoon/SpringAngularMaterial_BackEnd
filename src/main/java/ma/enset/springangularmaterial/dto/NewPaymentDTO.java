package ma.enset.springangularmaterial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.springangularmaterial.entities.PaymentType;

import java.time.LocalDate;

@Data @AllArgsConstructor@NoArgsConstructor
public class NewPaymentDTO {
    private LocalDate date;
    private double amount;
    private PaymentType paymentType;
    private String studentCode;
}
