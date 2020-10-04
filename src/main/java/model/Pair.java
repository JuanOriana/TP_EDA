package model;

import java.util.Objects;

public class Pair<T1,T2>{
    private T1 elem1;
    private T2 elem2;

    public Pair(T1 elem1, T2 elem2) {
        this.elem1 = elem1;
        this.elem2 = elem2;
    }

    public T1 getElem1() {
        return elem1;
    }

    public void setElem1(T1 elem1) {
        this.elem1 = elem1;
    }

    public T2 getElem2() {
        return elem2;
    }

    public void setElem2(T2 elem2) {
        this.elem2 = elem2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(elem1, pair.elem1) &&
                Objects.equals(elem2, pair.elem2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elem1, elem2);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "elem1=" + elem1 +
                ", elem2=" + elem2 +
                '}';
    }
}
