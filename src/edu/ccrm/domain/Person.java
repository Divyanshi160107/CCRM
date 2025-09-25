package edu.ccrm.domain;

// An abstract class cannot be turned into an object directly.
public abstract class Person {
    
    // Private fields demonstrate Encapsulation[cite: 59].
    private String fullName;
    private String email;

    // Constructor to initialize the object.
    public Person(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    // Abstract methods must be implemented by any subclass.
    public abstract void printDetails();

    // "Getters" and "Setters" to access the private fields.
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}