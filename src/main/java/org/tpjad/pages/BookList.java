package org.tpjad.pages;

import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.http.services.*;
import org.tpjad.entities.Book;

@Import(stylesheet="context:/book.css")
public class BookList{

    @Inject
    private Session session;

    @Property
    private Book book;

    @Inject 
    private Request req;

    public List<Book> getBooks()
    {
        String value = req.getParameter("value");
        String filter = req.getParameter("filter");
        if(value!=null&&filter!=null){
            switch(filter){
                case "genre":
                {
                    Query spSQLQuery = session.createQuery("from Book b WHERE b.genre=:value");
                    spSQLQuery.setString("value",value);
                    return spSQLQuery.list();
                    
                }
                    
                case "price":
                {
                    if(value.equals("d")){
                        return session.createQuery("from Book b ORDER BY b.price DESC").list();
                    }
                    return session.createQuery("from Book b ORDER BY b.price ASC").list();
                }
                default:
                    return session.createCriteria(Book.class).list();  
            }
        }
        return session.createCriteria(Book.class).list();
    }

}