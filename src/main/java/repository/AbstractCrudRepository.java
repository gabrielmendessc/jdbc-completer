package repository;

import java.util.List;

public abstract class AbstractCrudRepository<A,B> implements CrudRepository<A,B> {

    private RepositoryTemplate repositoryTemplate = new RepositoryTemplate();

    protected RepositoryTemplate getRepositoryTemplate() {

        return repositoryTemplate;

    }

    @Override
    public A insert(A domain) {

        throw new UnsupportedOperationException("Operation not implemented");

    }

    @Override
    public A update(A domain, B id) {

        throw new UnsupportedOperationException("Operation not implemented");

    }

    @Override
    public void delete(B id) {

        throw new UnsupportedOperationException("Operation not implemented");

    }

    @Override
    public List<A> findAll() {

        throw new UnsupportedOperationException("Operation not implemented");

    }

    @Override
    public A findById(B id) {

        throw new UnsupportedOperationException("Operation not implemented");

    }

}
