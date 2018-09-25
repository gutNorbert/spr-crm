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

	//be kell injekt�lnunk a session factoryt
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public List<Customer> getCustomers() {

		//hibernate session-t megkapni
		Session currentSession = sessionFactory.getCurrentSession();
		
		//lek�rdez�s kre�l�sa ... rendez�s last name alapj�n
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);
		
		//queryz�s �s visszakapni a result list-et
		List<Customer> customers = theQuery.getResultList();
		
		//visszat�rni az eredm�nyekkel
		return customers;
	}

	//ennyi k�d el�g hogy elments�k a vev�t hibernatet haszn�lva :)
	@Override
	public void saveCustomer(Customer theCustomer) {
		
		//megkapni a jelenlegi hibernate sessiont
		Session currentSession = sessionFactory.getCurrentSession();
		//elmenteni/updatelni a vev�t
		currentSession.saveOrUpdate(theCustomer);
		
	}

	@Override
	public Customer getCustomer(int theId) {
		//megkapni a jelenlegi hibernate sessiont
		Session currentSession = sessionFactory.getCurrentSession();
		//kiolvasni DB-b�l az objektumot els�dleges kulcsot haszn�lva
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		//megkapni a jelenlegi hibernate sessiont
		Session currentSession = sessionFactory.getCurrentSession();
		
		//t�r�lni az objektumot az els�dleges kulccsal
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
	        // Csak akkor keressen ha nem �res
	        //
	        if (theSearchName != null && theSearchName.trim().length() > 0) {

	            // firstName vagy lastName keres�s ... case insensitive
	            theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
	            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

	        }
	        else {
	            // ha a keres�mez� �res ... adja vissza az �sszes customert
	            theQuery =currentSession.createQuery("from Customer", Customer.class);            
	        }
	        
	        // query futtat�sa �s result list
	        List<Customer> customers = theQuery.getResultList();
	                
	        // return the results        
	        return customers;
	        
	    }

}
