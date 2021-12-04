public class Espectador {
    String[] infos;
    String cpf, nome, login, senha;
    long cpfFormatado;
    ListaAvaliacoes avaliacoes;

    public Espectador(String linha) {
        this.infos = linha.split(";");
        this.cpf = infos[0];
        this.nome = infos[1];
        this.login = infos[2];
        this.senha = infos[3];
        this.cpfFormatado = Long.parseLong(this.cpf.replace("-", ""));
        this.avaliacoes = new ListaAvaliacoes();
    }

    public boolean menorQue(Espectador outro) {
        return this.cpfFormatado < outro.cpfFormatado;
    }

    public boolean igual(Espectador outro) {
        return this.cpfFormatado == outro.cpfFormatado;
    }

    public String dadosEspectador() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nEspectador(a): " + this.nome + "\n" + "Login: " + this.login + "\n" + "CPF: " + this.cpf + "\n");
        sb.append("\nAvaliações:\n");
        sb.append(avaliacoes.dadosAvaliacoes());
        return sb.toString();
    }

    public String dadosEspectadorOrdenado() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nEspectador(a): " + this.nome + "\n" + "Login: " + this.login + "\n" + "CPF: " + this.cpf + "\n");
        sb.append("\nAvaliações:\n");
        sb.append(avaliacoes.dadosAvaliacoesOrdenado());
        return sb.toString();
    }
}
