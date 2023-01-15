package db.repository;

import java.util.List;

public class AbstractCrudRepository<A,B> implements CrudRepository<A,B> {

    private RepositoryTemplate repositoryTemplate = new RepositoryTemplate();

    protected RepositoryTemplate getRepositoryTemplate() {

        return repositoryTemplate;

    }

    @Override
    public A insert(A domain) {

        return null;

    }

    @Override
    public A update(A domain, B id) {

        return null;

    }

    @Override
    public void delete(B id) {

    }

    @Override
    public List<A> findAll() {

        return null;

    }

    @Override
    public A findById(B id) {

        return null;

    }

}
