public class Avaliacao {
    String cpf, nomeSerie;
    String[] infos;
    int epsAssistidos, avaliacao;

    Avaliacao(String linha) {
        this.infos = linha.split(";");
        this.cpf = infos[0];
        this.nomeSerie = infos[1];
        this.epsAssistidos = Integer.parseInt(infos[2]);
        this.avaliacao = Integer.parseInt(infos[3]);
    }

    public boolean igual(Avaliacao outra) {
        return (this.cpf == outra.cpf);
    }
}
