package com.techelevator.tenmo.model;

import java.util.Objects;

//This is a simple model class for an authority in a Spring Security application, which includes:
//A private field name representing the name of the authority
//A getter and a setter for the name field
//A constructor that takes a name parameter and sets the name field
//Override methods for equals, hashCode, and toString
public class Authority {

   private String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Authority(String name) {
      this.name = name;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Authority authority = (Authority) o;
      return name == authority.name;
   }

   @Override
   public int hashCode() {
      return Objects.hash(name);
   }

   @Override
   public String toString() {
      return "Authority{" +
              "name=" + name +
              '}';
   }
}
