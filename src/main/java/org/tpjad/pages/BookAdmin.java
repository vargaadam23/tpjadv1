package org.tpjad.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.tpjad.entities.Book;
import org.tpjad.services.interfaces.BookServiceInterface;

import java.util.List;

@RequiresRoles("manager")
public class BookAdmin {
    private static final Logger logger = LogManager.getLogger(Login.class);

    @Inject
    private AlertManager alertManager;

    @Inject
    BookServiceInterface bookService;

    @InjectComponent
    private Form book;

    @InjectComponent("name")
    private TextField nameField;

    @InjectComponent("author")
    private TextField authorField;

    @InjectComponent("price")
    private TextField priceField;

    @InjectComponent("genre")
    private TextField genreField;

    @InjectComponent("image")
    private TextField imageField;

    @Property
    private String name;

    @Property
    private String author;

    @Property
    private String price;

    @Property
    private String genre;

    @Property
    private String image;

    void onValidateFromBook()   //TO DO
    {
        if (name.length()<2)
            book.recordError(nameField, "Name too short");

        if (genre.length()<2)
            book.recordError(genreField, "Genre too short");
    }

    Object onSuccessFromBook()
    {
        Book book = new Book();
        book.setName(name);
        book.setGenre(genre);
        book.setPrice(Double.parseDouble(price));
        book.setImage(image);
        book.setAuthor(author);
        bookService.saveBook(book);
        return Index.class;
    }

    public List<Book> getBooks(){
       return bookService.getAll();
    }

    void onFailureFromBook()
    {
        logger.warn("Book error!");
        alertManager.error("I'm sorry but I can't book you in!");
    }

    //TO DO
}
