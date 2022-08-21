package query.entity;

import db.Result;
import query.Query;

public interface Entity {
    Result insert(Query qry) throws UnsupportedOperationException;
    Result update(Query qry) throws UnsupportedOperationException;
    Result delete(Query qry) throws UnsupportedOperationException;
    Result get(Query qry) throws UnsupportedOperationException;
    Result list() throws UnsupportedOperationException;
    Result exists(Query qry) throws UnsupportedOperationException;
}
