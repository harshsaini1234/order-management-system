package com.microservice.paymentservice.service;

import com.microservice.paymentservice.dto.PaymentRequest;
import com.microservice.paymentservice.dto.PaymentResponse;
import com.microservice.paymentservice.entity.Payment;
import com.microservice.paymentservice.enums.PaymentStatus;
import com.microservice.paymentservice.exception.PaymentNotFoundException;
import com.microservice.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponse createPayment(PaymentRequest request){
        Payment payment = new Payment();
        payment.setOrderId(request.orderId());
        payment.setUserId(request.userId());
        payment.setAmount(request.amount());
        payment.setPaymentMethod(request.paymentMethod());
        payment.setStatus(PaymentStatus.PENDING);
        Payment savedPayment = paymentRepository.save(payment);
        return mapToResponse(savedPayment);


    }
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(()->
                        new PaymentNotFoundException("Payment not found with the ID :"+id));

        return mapToResponse(payment);

    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByOrderId(Long id){
        return paymentRepository.findByOrderId(id)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    private PaymentResponse mapToResponse(Payment payment){
        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getCreateAt()
        );
    }

}
