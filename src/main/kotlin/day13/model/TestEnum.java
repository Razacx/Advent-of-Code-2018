package day13.model;

public enum TestEnum {

    ONE,
    TWO,
    THREE;

    private TestEnum next;

    static {
        ONE.next = TWO;
        TWO.next = THREE;
        THREE.next = ONE;
    }

}
