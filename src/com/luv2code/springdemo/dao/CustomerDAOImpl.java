package com.luv2code.springdemo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	//be kell injektálnunk a session factoryt
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public List<Customer> getCustomers() {

		//hibernate session-t megkapni
		Session currentSession = sessionFactory.getCurrentSession();
		
		//lekérdezés kreálása ... rendezés last name alapján
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);
		
		//queryzés és visszakapni a result list-et
		List<Customer> customers = theQuery.getResultList();
		
		//visszatérni az eredményekkel
		return customers;
	}

	//ennyi kód elég hogy elmentsük a vevõt hibernatet használva :)
	@Override
	public void saveCustomer(Customer theCustomer) {
		
		//megkapni a jelenlegi hibernate sessiont
		Session currentSession = sessionFactory.getCurrentSession();
		//elmenteni/updatelni a vevõt
		currentSession.saveOrUpdate(theCustomer);
		
	}

	@Override
	public Customer getCustomer(int theId) {
		//megkapni a jelenlegi hibernate sessiont
		Session currentSession = sessionFactory.getCurrentSession();
		//kiolvasni DB-bõl az objektumot elsõdleges kulcsot használva
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		//megkapni a jelenlegi hibernate sessiont
		Session currentSession = sessionFactory.getCurrentSession();
		
		//törölni az objektumot az elsõdleges kulccsal
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
	}
	
	 @Override
	    public List<Customer> searchCustomers(String theSearchName) {

	        // megkapni a jelenlegi hibernate sessiont
	        Session currentSession = sessionFactory.getCurrentSession();
	        
	        Query theQuery = null;
	        
	        //
	        // Csak akkor keressen ha nem üres
	        //
	        if (theSearchName != null && theSearchName.trim().length() > 0) {

	            // firstName vagy lastName keresés ... case insensitive
	            theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
	            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

	        }
	        else {
	            // ha a keresõmezö üres ... adja vissza az összes customert
	            theQuery =currentSession.createQuery("from Customer", Customer.class);            
	        }
	        
	        // query futtatása és result list
	        List<Customer> customers = theQuery.getResultList();
	                
	        // return the results        
	        return customers;
	        
	    }

}
