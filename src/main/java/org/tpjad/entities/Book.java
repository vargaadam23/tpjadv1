package org.tpjad.entities;

import javax.persistence.*;


import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;



@Entity
@Table(name="books")
public class Book
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Validate("required")
  public String name;

  @Validate("required")
  public String genre;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="order_id")
  public Order order;

  public String image;

  public String author;

  @Validate("required")
  public Double price;

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getPrice() {
    return price;
  }

  public Long getId() {
    return id;
  }

  public String getGenre() {
    return genre;
  }

  public String getImage() {
    return image;
  }

  public String getName() {
    return name;
  }

  public String getAuthor() {
    return author;
  }


}