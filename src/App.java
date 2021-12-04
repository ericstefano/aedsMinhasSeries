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
        TabelaHash thNome = TabelaHash.lerSeriesParaTabela("./series.txt", 521, "nome");
        TabelaHash thData = TabelaHash.lerSeriesParaTabela("./series.txt", 521, "data");

        lerAvaliacoes("./AvaliacoesSeries_1.txt", ab, thNome);
        Scanner sc = new Scanner(System.in);
        Pattern padraoCpf = Pattern.compile("(?<!.)\\d{9}-\\d{2}$"); // (?<!.)\d{9}-\d{2}$
        int input;
        limparTerminal();
        do {
            System.out.println("\033[0;37m" + "[o_o] (^_^) " + "\033[1;32m" + "[Minhas Séries]" + "\033[0;37m"
                    + " (\".\") ($.$)\n");
            System.out.println("\033[1;91m" + "0 - Sair");
            System.out.println("\033[1;92m" + "1 - Inserir avaliação");
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
                case 1:
                    limparTerminal();
                    String novoCpf;
                    System.out.println("\033[1;92m");
                    System.out.println("[Inserção de Avaliações]\n");
                    System.out.println(
                            "Digite o CPF do espectador que deseja inserir a avaliação seguindo o padrão:\n123456789-01");
                    if (sc.hasNext(padraoCpf)) {
                        novoCpf = sc.nextLine().strip();
                    } else {
                        erro("\033[1;91m" + "CPF inválido!\n");
                        limparBuffer(sc);
                        break;
                    }
                    Espectador pesquisaNovo = ab.pesquisarEspectador(novoCpf);
                    limparTerminal();
                    if (pesquisaNovo == null) {
                        erro("\033[1;91m" + "Espectador não encontrado!\n");
                        break;
                    } else {
                        System.out.println("Encontrei!");
                        System.out.println("\nDigite o Login deste espectador: ");
                        String login = sc.nextLine();
                        if (!login.equals(pesquisaNovo.login)) {
                            erro("\033[1;91m" + "Login inválido!\n");
                            break;
                        }

                        limparTerminal();
                        System.out.println("Digite a Senha deste espectador: ");
                        String senha = sc.nextLine();
                        if (!senha.equals(pesquisaNovo.senha)) {
                            erro("\033[1;91m" + "Senha inválida!\n");
                            break;
                        }

                        limparTerminal();
                        System.out.println(
                                "Digite o nome e a temporada da série que deseja inserir a avaliação seguindo o padrão:\nNome da Série - Temporada 1");
                        String nomeSerie = sc.nextLine();
                        Serie mockSerie = new Serie(String.format("%s;\"\";0", nomeSerie));
                        mockSerie.setHash("nome");
                        Serie pesquisaSerie = thNome.buscarSerie(mockSerie);
                        if (pesquisaSerie == null) {
                            erro("\033[1;91m" + "Série não encontrada!\n");
                            break;
                        }

                        limparTerminal();
                        System.out.println(
                                "Quantos episódios desta série você assistiu?\n" + pesquisaSerie.nome + " possui "
                                        + pesquisaSerie.qtdEps + " episódios");
                        System.out.println("\nInsira um valor entre 0 e " + pesquisaSerie.qtdEps);
                        int qtdEps = Integer.parseInt(sc.nextLine());
                        if (qtdEps < 0 || qtdEps > pesquisaSerie.qtdEps) {
                            erro("\033[1;91m" + "Quantidade de episódios inválida!\n");
                            break;
                        }

                        limparTerminal();
                        System.out.println(
                                "Qual a avaliação para " + pesquisaSerie.nome + "?\n");
                        System.out.println("Insira um valor entre 0 e 5");
                        int aval = Integer.parseInt(sc.nextLine());
                        if (aval < 0 || aval > 5) {
                            erro("\033[1;91m" + "Avaliação inválida!\n");
                            break;
                        }

                        Avaliacao objAval = new Avaliacao(
                                String.format("%s;%s;%s;%s;", pesquisaNovo.cpf, pesquisaSerie.nome, qtdEps, aval));
                        pesquisaNovo.avaliacoes.inserirPorUltimo(objAval);

                        if (objAval.epsAssistidos == pesquisaSerie.qtdEps) {
                            pesquisaSerie.inserirAvaliacao(objAval.avaliacao);
                        }

                        System.out.println("Avaliação inserida com sucesso!");
                    }
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
                        erro("\033[1;91m" + "CPF inválido!\n");
                        limparBuffer(sc);
                        break;
                    }

                    Espectador pesquisa = ab.pesquisarEspectador(cpf);
                    limparTerminal();
                    if (pesquisa == null) {
                        erro("\033[1;91m" + "Espectador não encontrado!\n");
                    } else {
                        System.out.println("Encontrei!");
                        System.out.println(pesquisa.dadosEspectadorOrdenado());
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
                        erro("\n\033[1;91m" + "Entrada inválida! 乁(x⏠x)ㄏ\n");
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
                        erro("\033[1;91m" + "Entrada inválida! 乁(x⏠x)ㄏ\n");
                        limparBuffer(sc);
                        break;
                    }
                    Serie mockSerieData = new Serie(String.format("\"\";%s;0", data));
                    mockSerieData.setHash("data");
                    ListaSeries pesquisaSeriesData = thData.buscarLista(mockSerieData);
                    limparTerminal();
                    if (pesquisaSeriesData.listaVazia()) {
                        erro("\033[1;91m" + "Nenhuma série encontrada!\n");
                    } else {
                        System.out.println("Encontrei!");
                        System.out.println(pesquisaSeriesData.dadosSeriesData(data));
                    }
                    break;
                default:
                    limparTerminal();
                    erro("\033[1;91m" + "Opção inválida! 乁(x⏠x)ㄏ\n");
            }
            if (input != 0) {
                System.out.println("Aperte enter para continuar");
                sc.nextLine();
            }
            limparTerminal();
        } while (input != 0);

        sc.close();
        limparTerminal();
        System.out.println("Fim! Obrigado por usar o Minhas Séries!");

        // TODO:
        // Trocar Quicksort pro Quicksort que o professor implementou
        // Implementar algoritmo pra bagunçar o algoritmo antes (evitar pior caso do
        // quicksort)
        // Testar entradas, possíveis bugs
        // Criar regex pra data
        // Número primos (pelo quantidade de linhas de séries) para as tabelas hash
        // Remover métodos que não estão sendo utilizados em todas as classes
        // Colocar pra entrada de senha ser invisivel ou com asterisco?
        // Validar entrada de quantidade de episódios (inserção de avaliação)
        // Validar enetrada de avaliação (inserção de avaliação)
        // Verificar com o professor o que fazer caso o usuário ja possua a avaliação.

        // Teste 1:
        // pesquisa espectador, 739298270-91
        // pesquisa por nome, Nihon Pretender - Temporada 2
        // pesquisa por data, 12/01/2018

        // Teste 2:
        // 739298270-91
        // RCair739
        // Cai500
        // East Goodies - Temporada 1
    }

}
