public class ListaAvaliacoes {

    public ElementoAvaliacao primeiro, ultimo;
    int tamanho = 0;

    public ListaAvaliacoes() {
        this.primeiro = new ElementoAvaliacao(null);
        this.ultimo = this.primeiro;
    }

    public boolean listaVazia() {
        return (this.primeiro == this.ultimo);
    }

    public void inserirPorUltimo(Avaliacao qual) {
        this.tamanho++;

        ElementoAvaliacao novo = new ElementoAvaliacao(qual);
        this.ultimo.proximo = novo;
        this.ultimo = novo;
    }

    public ElementoAvaliacao localizarPosicao(int posicao) {
        ElementoAvaliacao aux = this.primeiro;

        int pos = -2;
        while (((aux != this.ultimo) && ((pos + 1) != posicao))) {
            aux = aux.proximo;
            pos++;
        }
        return aux;
    }

    public void inserirNaPosicao(Avaliacao qual, int posicao) {
        this.tamanho++;

        ElementoAvaliacao novo = new ElementoAvaliacao(qual);
        ElementoAvaliacao aux = localizarPosicao(posicao);
        novo.proximo = aux.proximo;
        aux.proximo = novo;
        if (aux == this.ultimo)
            this.ultimo = novo;
    }

    public ElementoAvaliacao localizarDado(Avaliacao qual) {
        ElementoAvaliacao aux = this.primeiro;

        while (!aux.proximo.dados.igual(qual)) {
            aux = aux.proximo;
        }

        if (aux.proximo == null)
            return null;
        else
            return aux;
    }

    public Avaliacao retirarDado(Avaliacao qual) {
        this.tamanho--;

        if (listaVazia())
            return null;
        ElementoAvaliacao aux = localizarDado(qual);
        ElementoAvaliacao auxRet = aux.proximo;

        if (auxRet == null)
            return null;
        else {
            aux.proximo = auxRet.proximo;
            auxRet.proximo = null;

            if (auxRet == this.ultimo)
                this.ultimo = aux;

            return auxRet.dados;
        }

    }

    public Avaliacao pesquisar(Avaliacao qual) {
        ElementoAvaliacao aux = this.primeiro.proximo;

        while (aux != null && !aux.dados.igual(qual)) {
            aux = aux.proximo;
        }

        if (aux == null)
            return null;
        else
            return aux.dados;
    }

    public String dadosAvaliacoes() {
        if (listaVazia())
            return "Não possui avaliações!\n";

        StringBuilder sb = new StringBuilder();
        ElementoAvaliacao aux = this.primeiro.proximo;
        while (aux != null) {
            sb.append("\nNome da Série e Temporada: " + aux.dados.nomeSerie + "\n" + "Avaliação: " + aux.dados.avaliacao
                    + "\n" + "Quantidade de episódios assistidos: " + aux.dados.epsAssistidos + "\n");
            aux = aux.proximo;
        }
        return sb.toString();
    }

    public ElementoAvaliacao[] vetorAvaliacoes() {
        if (listaVazia())
            return null;

        ElementoAvaliacao[] vetor = new ElementoAvaliacao[this.tamanho];
        ElementoAvaliacao aux = this.primeiro.proximo;

        int i = 0;
        while (aux != null) {
            System.out.println(i);
            vetor[i] = aux;
            aux = aux.proximo;
            i++;
        }

        return vetor;
    }

    // public

}
