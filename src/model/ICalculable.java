package model;

public interface ICalculable {
    //atributos public static final
    int PRECIO_SANGRE = 100;
    int PRECIO_PLAQUETAS = 50;
    int PRECIO_PLASMA = 10;

    //metodos public abstract
    double totalMonto();
}
