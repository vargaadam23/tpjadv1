package org.tpjad.entities;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonVisual
    public Long id;

    @Validate("required")
    public String address;

    public Double total;

    @OneToMany(mappedBy="order",cascade = CascadeType.ALL)
    Set<Book> books;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    User user;

    public Double getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = calculateTotal();
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddress(String localitate, String judet, String str) {
        this.address = "Judet "+judet+", "+localitate+", "+str;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void addBook(Book book){
        Boolean ok = true;
        for(Book cpy : books){
            if(cpy.getId()== book.getId()){
                ok=false;
            }
        }
        if(ok){
            books.add(book);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double calculateTotal(){
        Double total = 0.0;
        for(Book book : books){
            total+=book.getPrice();
        }
        return total;
    }

    public void addOrderToBooks(){
        if(books!=null&&!books.isEmpty()){
            for(Book book : books){
                book.setOrder(this);
            }
        }
    }

}
