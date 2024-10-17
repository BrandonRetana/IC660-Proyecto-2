package tec.ic660.pagination.domain.valueObjects;

import java.util.ArrayList;

public class LimitedList<E> extends ArrayList<E> {
    private int maxSize;

    // Constructor que inicializa la lista con nulls
    public LimitedList(int maxSize) {
        super(maxSize); // Inicializamos la lista con el tamaño máximo
        this.maxSize = maxSize;
        
        // Inicializamos la lista con 'null' en cada posición
        for (int i = 0; i < maxSize; i++) {
            super.add(null);
        }
    }

    @Override
    public E set(int index, E element) {
        if (index >= maxSize) {
            throw new IndexOutOfBoundsException("Index exceeds max size: " + maxSize);
        }

        if (super.contains(element) && element != null) {
            System.out.println("Se duplico la juagada");
            System.exit(1);
        }
        return super.set(index, element);
    }

    // Evitamos agregar nuevos elementos, pero permitimos usar `set`
    @Override
    public boolean add(E element) {
        throw new UnsupportedOperationException("Use set() to modify existing elements.");
    }
}