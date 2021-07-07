package client;

/**
 * Interfata generica pentru design pattern-ul factory; cu acesta se poate
 * implementa cate un factory pentru tipuri diferite de obiecte (cum ar fi
 * masina si animal, care nu au nimic in comun)
 * @param <T> Obiectul pentru care se doreste a se face factory
 */
public interface Factory<T> {
    T factory(T obj);
}
