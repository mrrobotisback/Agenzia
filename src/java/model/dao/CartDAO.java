package model.dao;

import model.mo.Cart;
import model.dao.exception.DuplicatedObjectException;

public interface CartDAO {

    Cart insert(
            Long userId,
            Double total
    ) throws DuplicatedObjectException;

    boolean update(Cart cart, String field, String value);

    void delete(Cart card);

    Cart findByUserId(Long id);

}
