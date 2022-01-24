package org.tpjad.pages;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.http.services.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.tpjad.entities.Book;
import org.tpjad.entities.Order;
import org.tpjad.entities.User;
import org.tpjad.services.UserRealm;
import org.tpjad.services.UserService;
import org.tpjad.services.interfaces.BookServiceInterface;
import org.tpjad.services.interfaces.UserServiceInterface;

import javax.servlet.http.HttpSession;

@Import(stylesheet="context:/book.css")
public class BookList{

    private static final Logger logger = LogManager.getLogger(BookList.class);

    @Inject
    private UserServiceInterface userService;

    @Inject
    private BookServiceInterface bookService;

    @Property
    private Book book;

    @Inject 
    private Request req;

    public List<Book> getBooks()
    {
        String value = req.getParameter("value");
        String filter = req.getParameter("filter");
        if(value!=null&&filter!=null){ //Filtering not implemented yet
            switch(filter){
                case "genre":
                {
                   return bookService.getByGenre(value);
                }
                case "author":
                {
                    return bookService.getByAuthor(value);

                }
                    
                case "price":
                {
                    if(value.equals("d")){
                        return bookService.getByPrice(false);
                    }
                    return bookService.getByPrice(true);
                }
                default:
                    return bookService.getAll();
            }
        }
        return bookService.getAll();
    }


    @RequiresAuthentication
    @OnEvent(component = "addtocart")
    void addBook(Long id)
    {
        String username;
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = currentUser.getPrincipals();
        if (principalCollection != null && !principalCollection.isEmpty()) {
            Collection<Map> principalMaps = currentUser.getPrincipals().byType(Map.class);
            if (CollectionUtils.isEmpty(principalMaps)) {
                username = currentUser.getPrincipal().toString();
            }
            else {
                username = (String) principalMaps.iterator().next().get("username");
            }
        }else{
            username = null;
        }
        User user = null;
        if(username!=null){
            user = userService.findByUsername(username);
        }
        Book book = bookService.getById(id);
        if(book==null){
            logger.warn("Book not found, smth is not right!");
        }else{
            if(user == null){
                logger.warn("User not found, smth is not right!");
            }else{
                org.apache.tapestry5.http.services.Session sesh = req.getSession(false);
                if (sesh != null) {
                    Order cart = (Order) sesh.getAttribute("cart");
                    if(cart == null)
                    {
                        cart = new Order();
                        cart.setUser(user);
                        cart.setBooks(new HashSet<Book>());
                        cart.addBook(book);
                    }
                    else{
                        cart.addBook(book);
                    }
                    sesh.setAttribute("cart",cart);
                } else {
                    logger.warn("Session is null!");
                }
            }
        }


    }

}