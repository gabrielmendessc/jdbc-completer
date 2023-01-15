package db.repository;

import java.util.List;

public interface CrudRepository<A, B> {

    A insert(A domain);

    A update(A domain, B id);

    void delete(B id);

    List<A> findAll();

    A findById(B id);

}
