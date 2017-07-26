package com.msobdev.contactmgr;

import com.msobdev.contactmgr.model.Contact;
import com.msobdev.contactmgr.model.Contact.ContactBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by sob1 on 26.07.2017.
 */
public class Application {

    // Hold a reusable reference to a SessionFactory(since we need only one)
   // private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // Create a StandardServiceRegistry
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args){
        Contact contact = new ContactBuilder("name1", "surname1")
                .withEmail("email@gmail.com")
                .withPhone(5832902L)
                .build();
        System.out.println(contact);
    }
}
