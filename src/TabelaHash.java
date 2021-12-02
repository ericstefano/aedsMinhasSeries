import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TabelaHash {
    ListaSeries Vetor[];
    int tamanho;

    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        Vetor = new ListaSeries[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            Vetor[i] = new ListaSeries();
        }
    }

    public void inserir(Serie qual) {
        Vetor[qual.hash(this.tamanho)].inserirPorUltimo(qual);
    }

    public ListaSeries buscarLista(Serie qual) {
        return Vetor[qual.hash(this.tamanho)];
    }

    public Serie buscarSerie(Serie qual) {
        return buscarLista(qual).pesquisar(qual);
    }

    public static TabelaHash lerSeriesParaTabela(String arq, int tamanho) throws FileNotFoundException {
        File arquivo = new File(arq);
        Scanner sc = new Scanner(arquivo);
        TabelaHash th = new TabelaHash(tamanho);
        int linha = 1;
        while (sc.hasNextLine()) {
            Serie serie = new Serie(sc.nextLine());
            System.out.println(String.valueOf(linha) + " - " + serie.hash(tamanho));
            linha++;
            th.inserir(serie);
        }
        sc.close();
        return th;
    }

}
