// import java.text.SimpleDateFormat;
// import java.text.ParseException;
// import java.util.Date;

public class Serie {
    String[] infos;
    String nome, data;
    int qtdEps, espectadoresValidos;
    double avaliacaoMedia;

    public Serie(String linha) {
        this.infos = linha.split(";");
        this.nome = infos[0];
        this.data = infos[1];
        this.qtdEps = Integer.parseInt(infos[2]);
    }

    public boolean igual(Serie outra) {
        return (this.nome.toLowerCase().equalsIgnoreCase(outra.nome));
    }

    public String dadosSerie() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nNome e temporada: " + this.nome + "\n" + "Lançamento: " + this.data + "\n"
                + "Quantidade de episódios: " + this.qtdEps + "\n" + "Nota média: "
                + (avaliacaoMedia / espectadoresValidos) + "\n");
        return sb.toString();
    }

    public void inserirAvaliacao(int nota) {
        this.espectadoresValidos++;
        this.avaliacaoMedia += nota;
    }

    // public int hash(int modulo) {
    // SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    // Date dt = new Date();

    // try {
    // dt = formato.parse(this.data);
    // } catch (ParseException err) {
    // System.out.println("Data inválida!");
    // }

    // String epoch = String.valueOf(dt.getTime());
    // epoch = epoch.substring(0, epoch.length() - 5);
    // return (Integer.parseInt(epoch)) % modulo;
    // }

    public int hash(int modulo) {
        int hash = 0;
        String[] split = nome.split(" ");
        for (int i = 0; i < nome.length(); i++) {
            hash += split[split.length - 1].charAt(0) + (nome.charAt(i) * 13);
        }
        return nome.length() * hash % modulo;
    }

}