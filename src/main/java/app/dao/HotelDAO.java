package app.dao;

import app.config.HibernateConfig;
import app.entities.Hotel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class HotelDAO {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public Hotel findById(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Hotel.class, id);
    }

    public List<Hotel> findAll() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT h FROM Hotel h", Hotel.class)
                .getResultList();
    }

    public Hotel save(Hotel hotel) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (hotel.getId() == null) {
                em.persist(hotel);
            } else {
                hotel = em.merge(hotel);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
        return hotel;
    }


    public void delete(Hotel hotel) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(hotel) ? hotel : em.merge(hotel));
        em.getTransaction().commit();
    }
}
