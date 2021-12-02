import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ArvoreBinaria {
    Nodo raiz;
    ArvoreBinaria subarvoreEsquerda;
    ArvoreBinaria subarvoreDireita;

    public ArvoreBinaria(Espectador quem) {
        raiz = new Nodo(quem);
        subarvoreDireita = null;
        subarvoreEsquerda = null;
    }

    public ArvoreBinaria inserir(Espectador novo, ArvoreBinaria subarvore) {
        if (subarvore == null) {
            ArvoreBinaria novaSubarvore = new ArvoreBinaria(novo);
            return novaSubarvore;
        } else {
            if (novo.menorQue(subarvore.raiz.meuEspectador))
                subarvore.subarvoreEsquerda = inserir(novo, subarvore.subarvoreEsquerda);
            else
                subarvore.subarvoreDireita = inserir(novo, subarvore.subarvoreDireita);
        }
        return subarvore;
    }

    public Espectador buscar(Espectador quem, ArvoreBinaria subarvore) {
        if (subarvore == null) {
            return null;
        } else {
            if (quem.igual(subarvore.raiz.meuEspectador))
                return subarvore.raiz.meuEspectador;
            else {
                if (quem.menorQue(subarvore.raiz.meuEspectador))
                    return buscar(quem, subarvore.subarvoreEsquerda);
                else
                    return buscar(quem, subarvore.subarvoreDireita);
            }
        }
    }

    public static ArvoreBinaria lerEspectadoresParaArvore(String localizacao) throws FileNotFoundException {
        File dados = new File(localizacao);
        Scanner sc = new Scanner(dados);
        String linha = sc.nextLine();
        Espectador espec = new Espectador(linha);
        ArvoreBinaria ab = new ArvoreBinaria(espec);

        while (sc.hasNextLine()) {
            linha = sc.nextLine();
            espec = new Espectador(linha);
            ab.inserir(espec, ab);
        }
        sc.close();
        return ab;
    }

    public Espectador pesquisarEspectador(String cpf) {
        return this.buscar(new Espectador(String.format("%s;\"\";\"\";\"\"", cpf)), this);
    }

    public String dadosArvore(ArvoreBinaria subarvore) {
        if (subarvore == null)
            return "";
        else {
            String aux = dadosArvore(subarvore.subarvoreEsquerda);
            aux += subarvore.raiz.meuEspectador.dadosEspectador();
            aux += dadosArvore(subarvore.subarvoreDireita);
            return aux;
        }
    }
}
