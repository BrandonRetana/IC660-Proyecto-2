package tec.ic660.pagination.domain.valueObjects;

import java.util.ArrayList;

public class RealMemory<E> extends ArrayList<E> {
    private int maxSize;
    private int numberOfPages = 0;

    public RealMemory(int maxSize) {
        super(maxSize);
        this.maxSize = maxSize;
        
        for (int i = 0; i < maxSize; i++) {
            super.add(null);
        }
    }

    @Override
    public E set(int index, E element) {
        if (index >= maxSize) {
            throw new IndexOutOfBoundsException("Index exceeds max size: " + maxSize);
        }
        if (element == null){
            numberOfPages--;
        }
        if (element != null) {
            numberOfPages++;
        }
        return super.set(index, element);
    }

    @Override
    public boolean add(E element) {
        throw new UnsupportedOperationException("Use set() to modify existing elements.");
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    
}