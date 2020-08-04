package model.dao;

import model.mo.Have;

import java.util.List;

public interface HaveDAO {

    Have insert(
            Long userId,
            Long travelCode,
            Long quantity
    ) throws model.dao.exception.DuplicatedObjectException;

    boolean update(Have have, String field, String value);

    boolean update(Long travelCode, Long userId, int quantity);

    void delete(Have have);

    List<Have> find(Long userId);

    Have find(Long userId, Long travelCode);

}
