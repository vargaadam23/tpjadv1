package org.tpjad.entities;

import javax.persistence.*;


import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;



@Entity
@Table(name="books")
public class Book
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NonVisual
  public Long id;

  @Validate("required")
  public String name;

  @Validate("required")
  public String genre;

  public String image;

  @Validate("required")
  public Double price;
}