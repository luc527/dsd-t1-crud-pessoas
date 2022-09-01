package app.query.entity;

import app.db.Result;
import app.query.Query;

public interface Entity {
    Result insert(Query qry) throws UnsupportedOperationException;
    Result update(Query qry) throws UnsupportedOperationException;
    Result delete(Query qry) throws UnsupportedOperationException;
    Result get(Query qry) throws UnsupportedOperationException;
    Result list() throws UnsupportedOperationException;
    Result exists(Query qry) throws UnsupportedOperationException;
}
