import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    static void limparTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void limparBuffer(Scanner sc) {
        sc.nextLine();
    }

    static void erro(String msg) {
        limparTerminal();
        System.out.println(msg);
    }

    public static void inserirAvaliacao(Avaliacao aval, ArvoreBinaria ab, TabelaHash th) {
        Espectador mockEspec = new Espectador(String.format("%s;\"\";\"\";\"\"", aval.cpf));
        Espectador pesquisaEspec = ab.buscar(mockEspec, ab);

        if (pesquisaEspec != null) {
            pesquisaEspec.avaliacoes.inserirPorUltimo(aval);

            Serie mockSerie = new Serie(String.format("%s;\"\";0", aval.nomeSerie));
            mockSerie.setHash("nome");
            Serie pesquisaSerie = th.buscarSerie(mockSerie);
            if (pesquisaSerie != null) {
                if (aval.epsAssistidos == pesquisaSerie.qtdEps) {
                    pesquisaSerie.inserirAvaliacao(aval.avaliacao);
                }
            }
        }
    }

    static void lerAvaliacoes(String arquivo, ArvoreBinaria ab, TabelaHash th)
            throws FileNotFoundException {
        File dados = new File(arquivo);
        Scanner sc = new Scanner(dados);

        while (sc.hasNextLine()) {
            String linha = sc.nextLine();
            Avaliacao aval = new Avaliacao(linha);
            inserirAvaliacao(aval, ab, th);
        }
        sc.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArvoreBinaria ab = ArvoreBinaria.lerEspectadoresParaArvore("./Espectadores_2021-2.txt");
        TabelaHash thNome = TabelaHash.lerSeriesParaTabela("./series.txt", 997, "nome");
        TabelaHash thData = TabelaHash.lerSeriesParaTabela("./series.txt", 997, "data"); // 521

        lerAvaliacoes("./AvaliacoesSeries_1.txt", ab, thNome);
        Scanner sc = new Scanner(System.in);
        Pattern padraoCpf = Pattern.compile("(?<!.)\\d{9}-\\d{2}$"); // (?<!.)\d{9}-\d{2}$
        int input;
        limparTerminal();
        do {
            System.out.println("\033[0;37m" + "[o_o] (^_^) " + "\033[1;32m" + "[Minhas Séries]" + "\033[0;37m"
                    + " (\".\") ($.$)\n");
            System.out.println("\033[1;91m" + "0 - Sair");
            System.out.println("\033[1;94m" + "2 - Pesquisar espectador");
            System.out.println("\033[1;96m" + "3 - Pesquisar série por nome");
            System.out.println("\033[1;93m" + "4 - Pesquisar séries por data\n");
            System.out.println("\033[1;97m" + "Escolha uma opção: ");

            if (sc.hasNextInt()) {
                input = sc.nextInt();
            } else {
                input = -1;
            }
            limparBuffer(sc);
            limparTerminal();
            switch (input) {

                case 0:
                    break;
                case 2:
                    limparTerminal();
                    String cpf;
                    System.out.println("\033[1;94m");
                    System.out.println("[Pesquisa de Espectadores]\n");
                    System.out.println(
                            "Digite o CPF do espectador que deseja encontrar seguindo o padrão:\n123456789-01");
                    if (sc.hasNext(padraoCpf)) {
                        cpf = sc.nextLine().strip();
                    } else {
                        erro("\n\033[1;91m" + "\nEntrada inválida! 乁(x⏠x)ㄏ\n");
                        limparBuffer(sc);
                        break;
                    }

                    Espectador pesquisa = ab.pesquisarEspectador(cpf);
                    limparTerminal();
                    if (pesquisa == null) {
                        erro("\n\033[1;91m" + "Espectador não encontrado!\n");
                    } else {
                        System.out.println("Encontrei!");
                        System.out.println(pesquisa.dadosEspectador());
                    }
                    break;

                case 3:
                    limparTerminal();
                    String nomeETemp;
                    System.out.println("\033[1;96m");
                    System.out.println("[Pesquisa de Série por Nome]\n");
                    System.out.println(
                            "Digite o nome e a temporada da série que deseja encontrar no padrão a seguir:\nNome da Série - Temporada 1");
                    if (sc.hasNextLine()) {
                        nomeETemp = sc.nextLine().strip();
                    } else {
                        erro("\n\033[1;91m" + "\nEntrada inválida! 乁(x⏠x)ㄏ\n");
                        limparBuffer(sc);
                        break;
                    }
                    Serie mockSerieNome = new Serie(String.format("%s;\"\";0", nomeETemp));
                    mockSerieNome.setHash("nome");
                    Serie pesquisaSerieNome = thNome.buscarSerie(mockSerieNome);
                    limparTerminal();
                    if (pesquisaSerieNome == null) {
                        erro("\n\033[1;91m" + "Nenhuma série encontrada!\n");
                    } else {
                        System.out.println("Encontrei!");
                        System.out.println(pesquisaSerieNome.dadosSerie());
                    }
                    break;

                case 4:
                    String data;
                    System.out.println("\033[1;93m");
                    System.out.println("[Pesquisa de Séries por Data]\n");
                    System.out.println(
                            "Digite a data das séries que deseja encontrar no padrão a seguir:\ndd/mm/aaaa");

                    if (sc.hasNextLine()) {
                        data = sc.nextLine().strip();
                    } else {
                        erro("\n\033[1;91m" + "\nEntrada inválida! 乁(x⏠x)ㄏ\n");
                        limparBuffer(sc);
                        break;
                    }
                    Serie mockSerieData = new Serie(String.format("\"\";%s;0", data));
                    mockSerieData.setHash("data");
                    ListaSeries pesquisaSeriesData = thData.buscarLista(mockSerieData);
                    limparTerminal();
                    if (pesquisaSeriesData.listaVazia()) {
                        erro("\n\033[1;91m" + "Nenhuma série encontrada!\n");
                    } else {
                        System.out.println("Encontrei!");

                        System.out.println(pesquisaSeriesData.dadosSeriesData(data));
                    }
                    break;
                default:
                    limparTerminal();
                    erro("\n\033[1;91m" + "Opção inválida! 乁(x⏠x)ㄏ\n");
            }
            if (input != 0) {
                System.out.println("Aperte enter para continuar");
                sc.nextLine();
            }
            limparTerminal();
        } while (input != 0);

        sc.close();
        limparTerminal();
        System.out.println("Fim!");

        // Serie mockSerie = new Serie("The Call of the Pretender - Temporada
        // 7;12/01/2018;17");
        // mockSerie.setHash("data");
        // System.out
        // .println(thData.buscarLista(mockSerie)
        // .dadosSeries());

        // mockSerie.setHash("nome");
        // System.out
        // .println(thNome.buscarSerie(mockSerie)
        // .dadosSerie());

        // Espectador mockEspec = new Espectador(String.format("%s;\"\";\"\";\"\"",
        // "739298270-91"));

        // ElementoAvaliacao[] vetor = ab.buscar(mockEspec,
        // ab).avaliacoes.vetorAvaliacoes();

        // System.out.println(ab.buscar(mockEspec,
        // ab).dadosEspectador());
        // for (int i = 0; i < vetor.length; i++) {
        // System.out.println(vetor[i].dados.avaliacao);
        // }
    }
}
