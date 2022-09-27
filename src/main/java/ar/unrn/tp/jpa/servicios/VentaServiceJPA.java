package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.cache.CacheService;
import ar.unrn.tp.modelo.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VentaServiceJPA implements VentaService {
    private String persistenceUnit;
    private CacheService cacheService;

    public VentaServiceJPA(String persistenceUnit, CacheService cacheService) {
        this.persistenceUnit = persistenceUnit;
        this.cacheService = cacheService;
    }

    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            if (idCliente == null) {
                throw new RuntimeException("Servicio Venta - calcularMonto: No hay cliente");
            }

            if (idTarjeta == null) {
                throw new RuntimeException("Servicio Venta - calcularMonto: No hay tarjeta");
            }

            if (productos == null) {
                throw new RuntimeException("Servicio Venta - calcularMonto: No hay productos");
            }

            tx.begin();
            //hacer algo con em

            Cliente cliente = em.find(Cliente.class, idCliente);
            AbstractCobrable tarjeta = cliente.getTarjeta(idTarjeta);

            TypedQuery<Producto> productosReales = em.createQuery("select prod from Producto prod where prod.idProducto in :idProdsList", Producto.class);
            productosReales.setParameter("idProdsList", productos);
            List<Producto> prods = productosReales.getResultList();

            TypedQuery<Promocion> promos = em.createQuery("select promo from Promocion promo where " +
                    ":now between promo.fechaInicio and promo.fechaFin", Promocion.class);
            promos.setParameter("now", DateHelper.nowWithTime());
            List<Promocion> promocionList = promos.getResultList();

            int anioActual = LocalDate.now(ZoneId.systemDefault()).getYear();
            TypedQuery<CodigoUnico> codigoUnicoQuery = em.createQuery("from CodigoUnico where anio = :anioActual", CodigoUnico.class);
            codigoUnicoQuery.setParameter("anioActual", anioActual);
            codigoUnicoQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            CodigoUnico codigoUnico = null;

            try {
                codigoUnico = codigoUnicoQuery.getSingleResult();
                codigoUnico.aumentarCodigoUnico();
            } catch (NoResultException e) {
                codigoUnico = new CodigoUnico(1, anioActual);
            }

            CarroDeCompras carro = new CarroDeCompras(prods, cliente, DateHelper.nowWithTime(), promocionList,
                    tarjeta.getMetodo());
            Venta venta = carro.pagarCarrito();
            venta.setCodigoUnico(codigoUnico.devolverCodigoUnico());
            em.persist(venta);
            em.persist(codigoUnico);

            String redisClientKey = "ultimas:" + idCliente;
            cacheService.deleteCache(redisClientKey);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public float calcularMonto(List<Long> productos, Long idTarjeta) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        try {
            //hacer algo con em
            if (idTarjeta == null) {
                throw new RuntimeException("Servicio Venta - calcularMonto: No hay tarjeta");
            }

            AbstractCobrable tarjeta = em.find(AbstractCobrable.class, idTarjeta);

            if (productos == null) {
                throw new RuntimeException("Servicio Venta - calcularMonto: No hay productos");
            }

            TypedQuery<Promocion> promos = em.createQuery("select promo from Promocion promo where " +
                    ":now between promo.fechaInicio and promo.fechaFin", Promocion.class);
            promos.setParameter("now", DateHelper.nowWithTime());
            List<Promocion> promocionList = promos.getResultList();

            TypedQuery<Producto> prods = em.createQuery("select prod from Producto prod where prod.idProducto in :idProdsList", Producto.class);
            prods.setParameter("idProdsList", productos);
            List<Producto> productoList = prods.getResultList();

            CarroDeCompras carro = new CarroDeCompras(productoList, promocionList, tarjeta.getMetodo(), DateHelper.nowWithTime());
            return (float) carro.montoTotal();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public List ventas() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        try {
            //hacer algo con em
            TypedQuery<Venta> ventas = em.createQuery("select venta from Venta venta", Venta.class);
            return ventas.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public List ultimas(Long id) {
        // si no esta en jedis busca por jpa
        Gson gson = new Gson();
        String redisClientKey = "ultimas:" + id;
        String ultimasVentas = cacheService.getCache(redisClientKey);
        Type type = new TypeToken<ArrayList<VentaSimplificada>>() {
        }.getType();
        List<VentaSimplificada> ultimasVentasSimp = gson.fromJson(ultimasVentas, type);

        if (ultimasVentasSimp == null || ultimasVentasSimp.size() == 0) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
            EntityManager em = emf.createEntityManager();
            try {
                //hacer algo con em
                Cliente cliente = em.find(Cliente.class, id);
                TypedQuery<Venta> ventas = em.createQuery("select venta from Venta venta where " +
                        "venta.cliente = :clientParam order by venta.fechaVenta desc", Venta.class);
                ventas.setParameter("clientParam", cliente);
                ventas.setMaxResults(3);
                List<Venta> ultimasVentasJPA = ventas.getResultList();
                ultimasVentasSimp = new ArrayList<>();
                for (Venta venta : ultimasVentasJPA) {
                    ultimasVentasSimp.add(new VentaSimplificada(venta));
                }
                Collections.sort(ultimasVentasSimp);
                cacheService.setCache(redisClientKey, gson.toJson(ultimasVentasSimp));
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (em != null && em.isOpen())
                    em.close();
            }
        }

        return ultimasVentasSimp;
    }
}
