public class ListaSeries {

    public ElementoSerie primeiro, ultimo;

    public ListaSeries() {
        this.primeiro = new ElementoSerie(null);
        this.ultimo = this.primeiro;
    }

    public boolean listaVazia() {
        return (this.primeiro == this.ultimo);
    }

    public void inserirPorUltimo(Serie qual) {

        ElementoSerie novo = new ElementoSerie(qual);
        this.ultimo.proximo = novo;
        this.ultimo = novo;
    }

    public ElementoSerie localizarPosicao(int posicao) {
        ElementoSerie aux = this.primeiro;

        int pos = -2;
        while (((aux != this.ultimo) && ((pos + 1) != posicao))) {
            aux = aux.proximo;
            pos++;
        }
        return aux;
    }

    public void inserirNaPosicao(Serie qual, int posicao) {

        ElementoSerie novo = new ElementoSerie(qual);
        ElementoSerie aux = localizarPosicao(posicao);
        novo.proximo = aux.proximo;
        aux.proximo = novo;
        if (aux == this.ultimo)
            this.ultimo = novo;
    }

    public ElementoSerie localizarDado(Serie qual) {
        ElementoSerie aux = this.primeiro;

        while (!aux.proximo.dados.igual(qual)) {
            aux = aux.proximo;
        }

        if (aux.proximo == null)
            return null;
        else
            return aux;
    }

    public Serie retirarDado(Serie qual) {

        if (listaVazia())
            return null;
        ElementoSerie aux = localizarDado(qual);
        ElementoSerie auxRet = aux.proximo;

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

    public Serie pesquisar(Serie qual) {
        ElementoSerie aux = this.primeiro.proximo;

        while (aux != null && !aux.dados.igual(qual)) {
            aux = aux.proximo;
        }

        if (aux == null)
            return null;
        else
            return aux.dados;
    }

    public String dadosSeries() {
        if (listaVazia())
            return "Não encontrei nenhuma série!\n";

        StringBuilder sb = new StringBuilder();
        ElementoSerie aux = this.primeiro.proximo;
        while (aux != null) {
            sb.append(aux.dados.dadosSerie());
            aux = aux.proximo;
        }
        return sb.toString();
    }

    public String dadosSeriesData(String data) {
        if (listaVazia())
            return "Não encontrei nenhuma série!\n";

        StringBuilder sb = new StringBuilder();
        ElementoSerie aux = this.primeiro.proximo;
        while (aux != null) {
            if (aux.dados.data.equals(data))
                sb.append(aux.dados.dadosSerie());
            aux = aux.proximo;
        }
        return sb.toString();
    }
}
