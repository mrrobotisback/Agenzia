package model.dao;

import model.mo.Payment;

import java.util.List;

public interface PaymentDAO {

    Payment insert(
            Long number,
            String date,
            String amount,
            String with
    ) throws model.dao.exception.DuplicatedObjectException;

    boolean update(model.mo.Payment payment, String field, String value);

    void delete(model.mo.Payment payment);

    Payment findByPaymentNumber(Long number);
    List<model.mo.Payment> find(String field, String value);

    List<model.mo.Payment> allPayment();
}
