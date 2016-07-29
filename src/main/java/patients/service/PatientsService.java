package patients.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import patients.model.Insurance;
import patients.model.Patient;
import patients.model.Zip;

public enum PatientsService {

	INSTANCE;

	private static SessionFactory factory;
	
	static {
		factory = new Configuration().configure().buildSessionFactory();
	}

	public List<Patient> getPatients() {

		List<Patient> result = null;
		
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			result = (List<Patient>)session.createQuery("FROM Patient").getResultList();
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}


		return result;
	}

	public PatientSliceAndCount getPatientsSlice(LazyLoadData lazyLoadData) {
		
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			String searchStringParam = lazyLoadData.getSearchStringParam().trim();
			
			Query searchQuery = session.createQuery("FROM Patient where lastName like ? or firstName like ?");
			searchQuery.setParameter(0, "%" + searchStringParam + "%");
			searchQuery.setParameter(1, "%" + searchStringParam + "%");
			List<Patient> patients = (List<Patient>)searchQuery
										   .setFirstResult(lazyLoadData.getFirst())
										   .setMaxResults(lazyLoadData.getRows())
										   .getResultList();
			
			Query countQuery = session.createQuery("select count(p) from Patient p where lastName like ? or firstName like ?");
			countQuery.setParameter(0, "%" + searchStringParam + "%");
			countQuery.setParameter(1, "%" + searchStringParam + "%");
			Long count = (Long) countQuery.getSingleResult();
			
			tx.commit();
			
			return new PatientSliceAndCount(patients, count);
			
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return null;

	}
	
	public InsuranceSliceAndCount getInsurancesSlice(LazyLoadData lazyLoadData) {
		
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			String searchString = lazyLoadData.getSearchStringParam().trim();
			
			boolean possibleIK = false;
			int ik = 0;
			
			if (searchString.length() == 9) {
				possibleIK = true;
				try {
					ik = Integer.parseInt(searchString);
				} catch(NumberFormatException e) {
					possibleIK = false;
				}
			}
			
			Query searchQuery;
			Query countQuery;
			if (possibleIK) {
				searchQuery = session.createQuery("FROM Insurance where IKNumber = ?").setParameter(0, ik);
				countQuery = session.createQuery("select count(i) from Insurance i where IKNumber = ?").setParameter(0, ik);
			} else {
				searchQuery = session.createQuery("FROM Insurance where healthInsuranceName like ?")
									 .setParameter(0, "%" + searchString + "%");
				countQuery = session.createQuery("select count(i) from Insurance i where healthInsuranceName like ?")
									 .setParameter(0, "%" + searchString + "%");
			}

			
			List<Insurance> insurances = (List<Insurance>)searchQuery
					.setFirstResult(lazyLoadData.getFirst())
					.setMaxResults(lazyLoadData.getRows())
					.getResultList();
			
			Long count = (Long) countQuery.getSingleResult();
			
			tx.commit();
			
			return new InsuranceSliceAndCount(insurances, count);
			
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return null;
		
	}
	
	public void createPatient(Patient p) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			session.save(p);
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void updatePatient(Patient p) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			session.update(p);
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public ZipSliceAndCount getZipsSlice(LazyLoadData lazyLoadData) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			String searchString = lazyLoadData.getSearchStringParam().trim();
			
			boolean possibleZipNr = false;
			int zip = 0;
			
			if (searchString.length() == 5) {
				possibleZipNr = true;
				try {
					zip = Integer.parseInt(searchString);
				} catch(NumberFormatException e) {
					possibleZipNr = false;
				}
			}
			
			Query searchQuery;
			Query countQuery;
			if (possibleZipNr) {
				searchQuery = session.createQuery("FROM Zip where zip = ?").setParameter(0, zip);
				countQuery = session.createQuery("select count(z) from Zip z where zip = ?").setParameter(0, zip);
			} else {
				searchQuery = session.createQuery("FROM Zip where city like ?")
									 .setParameter(0, "%" + searchString + "%");
				countQuery = session.createQuery("select count(i) from Zip i where city like ?")
									 .setParameter(0, "%" + searchString + "%");
			}

			
			List<Zip> zips = (List<Zip>)searchQuery
					.setFirstResult(lazyLoadData.getFirst())
					.setMaxResults(lazyLoadData.getRows())
					.getResultList();
			
			Long count = (Long) countQuery.getSingleResult();
			
			tx.commit();
			
			return new ZipSliceAndCount(zips, count);
			
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return null;
	}

	public void deleteArticle(int internalNumber) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			Patient p = (Patient)session.load(Patient.class, internalNumber);
			session.delete(p);
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}

}
