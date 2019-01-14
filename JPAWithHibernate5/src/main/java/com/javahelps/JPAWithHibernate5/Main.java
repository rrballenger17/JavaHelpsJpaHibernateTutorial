package com.javahelps.JPAWithHibernate5;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    // Create an EntityManagerFactory when you start the application.
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY;

    public static void main(String[] args) {

    	try {
    		ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("JavaHelps");
    	}catch(Exception e) {
    		e.printStackTrace();
    		System.exit(1);
    		
    	}
    	delete(1);
    	delete(2);
    	delete(3);

    	
        // Create two Students
        create(1, "Alice", 22);     // Alice will get an id 1
        create(2, "Bob", 20);       // Bob will get an id 2
        create(3, "Charlie", 25);   // Charlie will get an id 3

        // Update the age of Bob using the id
        upate(2, "Bob", 25);

        // Delete the Alice from database
        delete(1);

        // Print all the Students
        List<Student> students = readAll();
        if (students != null) {
            for (Student stu : students) {
                System.out.println(stu);
            }
        }
        

        List<Student> results = searchFor("name", "Charlie");
        
        if(results.isEmpty() || results.size() > 1) {
        	System.out.println("Error: No or too many results in search by name: " + results.size());
        	//System.exit(1);
        }else if(!results.get(0).getName().contentEquals("Charlie")) {
        	System.out.println("Error: Search by name did not return expected value");
        	//System.exit(1);	
        }else {
        	System.out.println("Success: Search by column and value found the expected result");
        }

        // NEVER FORGET TO CLOSE THE ENTITY_MANAGER_FACTORY
        ENTITY_MANAGER_FACTORY.close();
    }
    
    
    
    /**
     * Search by column and value
     * 
     * @param id
     */
    public static List<Student> searchFor(String columnName, String columnValue) {

        List<Student> students = null;

        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get and begin transaction
            transaction = manager.getTransaction();
            transaction.begin();

            // Create and execute query for students
            // Get the result list
            students = manager.createQuery("SELECT s FROM Student s where s." + columnName + " LIKE :value", Student.class)
            		.setParameter("value", columnValue).getResultList();

            
//            createQuery(
//            	    "SELECT c FROM Customer c WHERE c.name LIKE :custName")
//            	    .setParameter("custName", name)
//            	    .setMaxResults(10)
//            	    .getResultList();
            
            
            // Commit transaction
            transaction.commit();
        } catch (Exception ex) {

        	if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return students;
    }
    
    public static Student lastStudent = null;
    

    /**
     * Create a new Student.
     * 
     * @param name
     * @param age
     */
    public static void create(int id, String name, int age) {
        
    	// Create the EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        
        EntityTransaction transaction = null;

        try {
            // Get and begin a transaction
            transaction = manager.getTransaction();
            transaction.begin();

            // new Student object
            Student stu = new Student();
            stu.setId(id);
            stu.setName(name);
            stu.setAge(age);
            
            lastStudent = stu;

            // persist the student with the entity manager 
            manager.persist(stu);

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {

        	if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
            System.exit(1);
        } finally {
            // Close the EntityManager
            manager.close();
        }
    }

    /**
     * Read all the Students.
     * 
     * @return a List of Students
     */
    public static List<Student> readAll() {

        List<Student> students = null;

        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get and begin transaction
            transaction = manager.getTransaction();
            transaction.begin();

            // Create and execute query for students
            // Get the result list
            students = manager.createQuery("SELECT s FROM Student s", Student.class).getResultList();

            // Commit transaction
            transaction.commit();
        } catch (Exception ex) {

        	if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return students;
    }

    /**
     * Delete the existing Student.
     * 
     * @param id
     */
    public static void delete(int id) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get and begin a transaction
            transaction = manager.getTransaction();
            transaction.begin();

            // Find and remove the student
            Student stu = manager.find(Student.class, id);
            manager.remove(stu);

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {

        	if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
    }

    /**
     * Update the existing Student.
     * 
     * @param id
     * @param name
     * @param age
     */
    public static void upate(int id, String name, int age) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get and begin a transaction
            transaction = manager.getTransaction();
            transaction.begin();

            // Find persisted student by id
            Student stu = manager.find(Student.class, id);

            // Update values
            stu.setName(name);
            stu.setAge(age);

            // Persist the updated student
            manager.persist(stu);

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {

        	if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
    }
}