package org.tpjad.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.http.services.Request;
import org.apache.tapestry5.http.services.Session;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.tpjad.entities.Book;
import org.tpjad.entities.Order;
import org.tpjad.entities.User;
import org.tpjad.services.interfaces.UserServiceInterface;

import java.util.*;

@RequiresAuthentication
@Import(stylesheet="context:/cart.css")
public class Cart {

    private static final Logger logger = LogManager.getLogger(Cart.class);

    @Inject
    Request req;

    @Inject
    org.hibernate.Session session;

    @Inject
    UserServiceInterface userService;

    @Parameter
    Order cart;

    @Parameter
    String message;

    @Property
    private Book book;

    @Property
    private User user;

    @Parameter
    String total;

    @InjectComponent
    private Form order;

    @InjectComponent("judet")
    private TextField judetField;

    @InjectComponent("city")
    private TextField cityField;

    @InjectComponent("address")
    private TextField addressField;

    @Property
    private String disabled;

    @Property
    private String judet;

    @Property
    private String city;

    @Property
    private String address;

    public String getMessage(){
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Set<Book> getBooks(){
        return cart.getBooks();
    }

    public String getTotal(){
        return total;
    }

    public String getUsername(){
        return user.getUsername();
    }

    public String getEmail(){
        return user.getEmailAddress();
    }

    public String getPhone(){
        return user.getPhone();
    }

    public String getName(){
        return user.getFirstName()+" "+user.getLastName();
    }

    void onValidateFromOrder()
    {
        if (judet.length()<2)
            order.recordError(judetField, "County/Region too short");

        if (city.length()<2)
            order.recordError(cityField, "City too short");

        if (address.length()<2)
            order.recordError(addressField, "Address too short");
    }

    Object onSuccessFromOrder()
    {
        fetchCart();
        cart.setTotal();
        cart.setAddress(city,judet,address);
        cart.addOrderToBooks();
        session.save(cart);
        session.getTransaction().commit();
        org.apache.tapestry5.http.services.Session sesh = req.getSession(false);
        sesh.setAttribute("cart",null);
        return Index.class;
    }
    void onFailureFromOrder()
    {
        logger.warn("Order error!");
    }


    @SetupRender
    void initializeValue()
    {
        String username = null;
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
        }
        user = userService.findByUsername(username);
        if(user==null){
            logger.warn("User is null!");
        }else{
            fetchCart();
        }

    }



    @OnEvent(component = "delete")
    void deleteFromCart(Long id)
    {
        fetchCart();
        if(cart.getBooks()!=null&&!cart.getBooks().isEmpty()){
            Set<Book> booksDe = cart.getBooks();
            Set<Book> copy = new HashSet<Book>();
            for(Book book : booksDe){
                if(book.getId()!=id){
                    copy.add(book);
                }
            }
            cart.setBooks(copy);
        }

    }

    private void fetchCart(){
        org.apache.tapestry5.http.services.Session sesh = req.getSession(false);
        if (sesh != null) {
            cart = (Order) sesh.getAttribute("cart");
            if (cart == null) {
                cart = new Order();
                cart.setTotal(0.0);
                this.total = cart.getTotal().toString();
                message = "No items in cart";
                disabled= "disabled";
            } else {
                if(cart.getBooks().isEmpty()){
                    message = "No items in cart";
                    disabled= "disabled";
                }else{
                    message = "Shopping Cart";
                    disabled= "";
                }
                cart.setTotal();
                this.total = cart.getTotal().toString();

            }
        }
    }
}
