public class ElementoAvaliacao {
    public Avaliacao dados;
    public ElementoAvaliacao proximo;

    public ElementoAvaliacao(Avaliacao dado) {
        this.dados = dado;
        this.proximo = null;
    }
}
