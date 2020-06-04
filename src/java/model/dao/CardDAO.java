package model.dao;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Card;

import java.util.List;

public interface CardDAO {

    Card insert(
            Long number,
            String holder,
            int cvv,
            String expiration,
            Long userId
    ) throws DuplicatedObjectException;

    boolean update(Card card, String field, String value);

    void delete(Card card);

    Card findByUserId(Long id);
    List<Card> find(Long userId,String field, String value);

}
