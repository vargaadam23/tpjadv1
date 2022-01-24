package org.tpjad.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.tpjad.entities.Book;
import org.tpjad.entities.User;
import org.tpjad.services.interfaces.BookServiceInterface;

import java.util.List;

public class BookService implements BookServiceInterface {

    private Session session;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public BookService(Session session) {
        this.session=session;
    }

    public List<Book> getByGenre(String genre){
        Criteria crit = session.createCriteria(Book.class);
        crit.add(Restrictions.eq("genre",genre));
        crit.add(Restrictions.isNull("order"));
        List<Book> results = crit.list();
        return results;
    }

    public List<Book> getByAuthor(String author){
        Criteria crit = session.createCriteria(Book.class);
        crit.add(Restrictions.eq("author",author));
        crit.add(Restrictions.isNull("order"));
        List<Book> results = crit.list();
        return results;
    }

    public List<Book> getByPrice(Boolean asc){
        Criteria crit = session.createCriteria(Book.class);
        if(asc){
            crit.addOrder(Order.asc("price"));
        }else{
            crit.addOrder(Order.desc("price"));
        }
        crit.add(Restrictions.isNull("order"));
        List<Book> results = crit.list();
        return results;
    }


    public List<Book> getAll(){
        Criteria crit = session.createCriteria(Book.class);
        crit.add(Restrictions.isNull("order"));
        List<Book> results = crit.list();
        return results;
    }

    public Book getById(Long id){
        Criteria crit = session.createCriteria(Book.class);
        crit.add(Restrictions.eq("id",id));
        crit.add(Restrictions.isNull("order"));
        List<Book> results = crit.list();
        return results.size()<=0?null:results.get(0);
    }

    public void saveBook(Book book){
        session.save(book);
        session.getTransaction().commit();
    }

}
