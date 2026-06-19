public interface IObservable {
    
    void agregar(Observador o);
    void eliminar(Observador o);

    void notificar();

}
