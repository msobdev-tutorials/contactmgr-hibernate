package com.msobdev.contactmgr;

import com.msobdev.contactmgr.model.Contact;
import com.msobdev.contactmgr.model.Contact.ContactBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by sob1 on 26.07.2017.
 */
public class Application {

    // Hold a reusable reference to a SessionFactory(since we need only one)
    private static final SessionFactory sessionFactory = buildSessionFactory();

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
        int id = save(contact);

        // Display a list of contacts before the update
        System.out.println("Before udpate");
        fetchAllContacts().forEach(System.out::println);

        // Get the persisted contact
        Contact c = findContactById(id);

        // Update the contact
        c.setFirstName("UpdatedName");

        // Persist the changes
        System.out.println("Updating");
        update(c);

        // Display a list of contacts after the update
        System.out.println("Update complete!");
        fetchAllContacts().forEach(System.out::println);

        // Persist the deletion
        System.out.println("Deleting");
        delete(c);
        
        // Display a list of contacts after the delete
        System.out.println("Delete complete!");
        fetchAllContacts().forEach(System.out::println);
    }

    private static Contact findContactById(int id){
        // Open a session
        Session session = sessionFactory.openSession();

        // Retrieve the persistent object(or null if not found)
        Contact contact = session.get(Contact.class, id);

        // Close the session
        session.close();

        // Return the object
        return contact;
    }

    private static void update(Contact contact){
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a session
        session.beginTransaction();

        // Use the session to update the contact
        session.update(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();

    }

    private static void delete(Contact contact){
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a session
        session.beginTransaction();

        // Use the session to update the contact
        session.delete(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    private static List<Contact> fetchAllContacts(){
        // Open a session
        Session session = sessionFactory.openSession();

        // Create Criteria object
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = builder.createQuery(Contact.class);

        Root<Contact> contactRoot = criteria.from(Contact.class);
        criteria.select(contactRoot);

        // Get a list of Contact objects according to the Criteria object
        List<Contact> contacts = session.createQuery(criteria).list();

        // Close the session
        session.close();

        return contacts;
    }

    private static int save(Contact contact){
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to save the contact
        int id = (int)session.save(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();

        return id;
    }
}
