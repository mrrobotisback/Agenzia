package model.dao;

import model.mo.Have;

public interface HaveDAO {

    Have insert(
            Long userId,
            Long travelCode,
            Long quantity
    ) throws model.dao.exception.DuplicatedObjectException;

    boolean update(Have have, String field, String value);

    void delete(Have have);

    Have find(Long userId);

    Have find(Long userId, Long travelCode);

}
