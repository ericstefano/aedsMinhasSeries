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
        System.out.println(msg);
    }

    public static void inserirAvaliacao(Avaliacao aval, ArvoreBinaria ab, TabelaHash th) {
        String mockEspec = String.format("%s;\"\";\"\";\"\"", aval.cpf);
        Espectador pesquisaEspec = ab.buscar(new Espectador(mockEspec), ab);

        if (pesquisaEspec != null) {
            pesquisaEspec.avaliacoes.inserirPorUltimo(aval);

            String mockSerie = String.format("%s;\"\";0", aval.nomeSerie);
            Serie pesquisaSerie = th.buscarSerie(new Serie(mockSerie));
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
        TabelaHash th = TabelaHash.lerSeriesParaTabela("./series.txt", 521); // 521 é o número primo mais próximo da
                                                                             // quantidade de linhas no arquivo séries
        lerAvaliacoes("./AvaliacoesSeries_1.txt", ab, th);
        Scanner sc = new Scanner(System.in);
        Pattern padraoCpf = Pattern.compile("(?<!.)\\d{9}-\\d{2}$"); // (?<!.)\d{9}-\d{2}$
        String cpf, nome, login, senha;
        int input;

        limparTerminal();

        do {
            System.out.println("\033[0;37m" + "[o_o] (^_^) " + "\033[1;32m" + "[Ｍｉｎｈａｓ Ｓéｒｉｅｓ]" + "\033[0;37m"
                    + " (\".\") ($.$)\n");
            System.out.println("\033[1;91m" + "0 - Sair");
            System.out.println("\033[1;93m" + "1 - Inserir novo espectador");
            System.out.println("\033[1;94m" + "2 - Pesquisar espectador\n");
            System.out.println("\033[1;97m" + "Escolha uma opção: ");

            if (sc.hasNextInt()) {
                input = sc.nextInt();
            } else {
                input = -1;
            }
            limparBuffer(sc);

            switch (input) {

                case 0:
                    break;

                case 1:
                    System.out.println("\033[1;93m");
                    System.out.println("Digite um CPF no padrão a seguir:\n123456789-01");
                    if (sc.hasNext(padraoCpf)) {
                        cpf = sc.nextLine().strip();
                    } else {
                        System.out.println("\nEntrada inválida! 乁(x⏠x)ㄏ\n");
                        limparBuffer(sc);
                        break;
                    }

                    System.out.println("Digite um nome pro usuário:");
                    if (sc.hasNextLine()) {
                        nome = sc.nextLine().strip();
                    } else {
                        System.out.println("\nEntrada inválida! 乁(x⏠x)ㄏ\n");
                        limparBuffer(sc);
                        break;
                    }
                    break;

                case 2:
                    System.out.println("Digite um cpf no padrão a seguir:\n123456789-01");
                    if (sc.hasNext(padraoCpf)) {
                        cpf = sc.nextLine().strip();
                    } else {
                        System.out.println("\nEntrada inválida! 乁(x⏠x)ㄏ\n");
                        limparBuffer(sc);
                        break;
                    }

                    Espectador pesquisa = ab.pesquisarEspectador(cpf);
                    if (pesquisa == null)
                        System.out.println("\nEspectador não encontrado!\n");
                    else
                        System.out.println(pesquisa.dadosEspectador());
                    break;

                default:
                    System.out.println("\n\033[1;91m" + "Opção inválida! 乁(x⏠x)ㄏ\n");
            }
        } while (input != 0);

        sc.close();
        limparTerminal();
        System.out.println("Fim!");

        System.out
                .println(th.buscarSerie(new Serie("The Walking Minds - Temporada 4;11/02/2013;5"))
                        .dadosSerie());

    }
}
