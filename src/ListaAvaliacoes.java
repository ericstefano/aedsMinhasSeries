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

    public ElementoAvaliacao localizarDadoPorNome(Avaliacao qual) {
        ElementoAvaliacao aux = this.primeiro.proximo;

        while (aux != null && !aux.dados.nomeIgual(qual)) {
            aux = aux.proximo;
        }

        if (aux == null)
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

    public String dadosAvaliacoes() {
        if (listaVazia())
            return "N??o possui avalia????es!\n";

        StringBuilder sb = new StringBuilder();
        ElementoAvaliacao aux = this.primeiro.proximo;
        while (aux != null) {
            sb.append("\nNome da S??rie e Temporada: " + aux.dados.nomeSerie + "\n"
                    + "Quantidade de epis??dios assistidos: " + aux.dados.epsAssistidos + "\n" + "Avalia????o: "
                    + aux.dados.avaliacao + "\n");
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
            vetor[i] = aux;
            aux = aux.proximo;
            i++;
        }

        return vetor;
    }

    public String dadosAvaliacoesOrdenado() {
        if (listaVazia())
            return "N??o possui avalia????es!\n";

        ElementoAvaliacao[] vet = vetorAvaliacoes();
        Quicksort.sort(vet, 0, vet.length - 1);

        StringBuilder sb = new StringBuilder();
        for (int i = vet.length - 1; i >= 0; i--) {
            sb.append("\nNome da S??rie e Temporada: " + vet[i].dados.nomeSerie
                    + "\n" + "Quantidade de epis??dios assistidos: " + vet[i].dados.epsAssistidos + "\n" + "Avalia????o: "
                    + vet[i].dados.avaliacao + "\n");
        }
        return sb.toString();
    }

}
