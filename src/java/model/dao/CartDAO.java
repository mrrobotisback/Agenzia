package model.dao;

import model.mo.Cart;

public interface CartDAO {

    Cart insert(
            Long userId,
            Double total
    ) throws model.dao.exception.DuplicatedObjectException;

    boolean update(Cart cart, String field, String value);

    void delete(Cart card);

    Cart findByUserId(Long id);

}
