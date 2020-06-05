package model.dao;

import model.mo.Details;

public interface DetailsDAO {

    Details insert(
            Long orderNumber,
            Long travelCode,
            Long quantity
    ) throws model.dao.exception.DuplicatedObjectException;

    boolean update(Details details, String field, String value);

    void delete(Details details);

    Details find(Long orderNumber);

    Details find(Long orderNumber, Long travelCode);

}
