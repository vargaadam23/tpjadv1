package org.tpjad.services.interfaces;

import org.tpjad.entities.Book;

import java.util.List;

public interface BookServiceInterface {

    public List<Book> getByGenre(String genre);

    public List<Book> getByPrice(Boolean asc);

    public List<Book> getAll();

    public Book getById(Long id);

    public List<Book> getByAuthor(String author);

    public void saveBook(Book book);
}
