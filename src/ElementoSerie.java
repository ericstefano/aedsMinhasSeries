public class ElementoSerie {
    public Serie dados;
    public ElementoSerie proximo;

    public ElementoSerie(Serie dado) {
        this.dados = dado;
        this.proximo = null;
    }
}
