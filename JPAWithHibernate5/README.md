
NOTES:

JPA with the latest Hibernate v5.2.3

Java 1.8, Eclipse for Java EE developers and MySQL server

Create a database “javahelps” and a table “student” in MySQL.

CREATE DATABASE IF NOT EXISTS javahelps;

CREATE  TABLE javahelps.student ( 
student_id INT NOT NULL ,
student_name VARCHAR(45) NOT NULL ,
student_age INT NOT NULL ,
PRIMARY KEY (student_id) );


groupId: artifactId

dependencies
- ch.qos.logback: logback-classic
- org.hibernate: hibernate-core
- org.hibernate: hibernate-entitymanager
- mysql: mysql-connector-java

plugin
- org.apache.maven.plugins: maven-compiler-plugin

persistence.xml in src/main/resources/META-INF
	- persistence-unit name
	- provider org.hibernate.jpa.HibernatePersistenceProvider
	- class com.javahelps.JPAWithHibernate5.Student
	- property javax.persistence.jdbc.url jdbc:mysql://localhost...
	- property javax.persistence.jdbc.user
	- property javax.persistence.jdbc.password

EntityManagerFactory - Interface used to interact with the entity manager factory for the persistence unit.
EntityManager - Interface used to interact with the persistence context.

Added additional feature beyond tutorial: 
 public static List<Student> searchFor(String columnName, String columnValue)


JPA Annotations
@Entity - on class
@Table(name = "student") - on class
@Id - primary key
@Column(name = "student_id", unique = true) - on each instance variable for column 







