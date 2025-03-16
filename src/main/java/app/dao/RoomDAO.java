package app.dao;

import app.config.HibernateConfig;
import app.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


public class RoomDAO {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public Room findById(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Room.class, id);
    }

    public Room save(Room room) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (room.getId() == null) {
                em.persist(room);
            } else {
                room = em.merge(room);
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
        return room;
    }


    public void delete(Room room) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(room) ? room : em.merge(room));
        em.getTransaction().commit();
    }
}
