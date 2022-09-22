package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.*;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class VentaServiceJPA implements VentaService {
    private String persistenceUnit;

    public VentaServiceJPA(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
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
        // agregar el id en jedis, la key es ultimasVentas, entonces cualquier cliente que la acceda ve lo de otro en la escritura y lectura
        // no hacer un for con llamadas a base de datos por que es ineficiente, crear la coleccion con lo que necesito y lo agrego una ves
        // filtrar los datos de las ventas que necesite, no es necesario el cliente por ejemplo, o crear otro objeto "VentaSimplificada" con esos datos asi es mas facil de parsear
        Jedis jedis = new Jedis("redis://default:redispw@localhost:49153");
        // aca taria mal la key
        List<String> ultimasVentas = jedis.zrange("ultimasVentas", 0, -1);
        Gson gson = new Gson();

        if (ultimasVentas.size() == 0) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
            EntityManager em = emf.createEntityManager();
            try {
                //hacer algo con em
                Cliente cliente = em.find(Cliente.class, id);
                TypedQuery<Venta> ventas = em.createQuery("select venta from Venta venta where venta.cliente = :clientParam order by venta.fechaVenta desc", Venta.class);
                ventas.setParameter("clientParam", cliente);
                ventas.setMaxResults(3);
                List<Venta> ultimasVentasJPA = ventas.getResultList();
                for (Venta venta : ultimasVentasJPA) {
                    // aca taria mal 2 cosas, la key y llamada a bd dentro de un for
                    jedis.zadd("ultimasVentas", (double)venta.getFechaVenta().getTime(), gson.toJson(venta));
                }

                return ultimasVentasJPA;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (em != null && em.isOpen())
                    em.close();
            }
        }
        else {
            List<Venta> ultimasVentasParsed = new ArrayList<>();
            for (String venta : ultimasVentas) {
                ultimasVentasParsed.add(gson.fromJson(venta, Venta.class));
            }
            return ultimasVentasParsed;
        }
    }
}
