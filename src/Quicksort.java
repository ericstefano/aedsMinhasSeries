public class Quicksort {
    public static int partition(ElementoAvaliacao[] A, int p, int r) {
        int x = A[p].dados.avaliacao;
        int i = p;
        int j = r;
        while (true) {

            while (A[i].dados.avaliacao > x) {
                i++;
            }

            while (A[j].dados.avaliacao < x) {
                j--;
            }
            if (i < j) {
                ElementoAvaliacao temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            } else {
                return j;
            }
        }
    }

    public static void sort(ElementoAvaliacao[] A, int p, int r) {
        if (p < r) {
            int q = partition(A, p, r);
            sort(A, p, q);
            sort(A, q + 1, r);
        }
    }
}
