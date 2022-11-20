package src.db.repository;

import src.db.client.DBClient;

public abstract class Repository {
    protected final DBClient client;

    public Repository(DBClient client) {
        this.client = client;
    }

}
