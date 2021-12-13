public class Quicksort {
    static int i, j;

    public static void partition(ElementoAvaliacao[] arr, int l, int r) {

        i = l - 1;
        j = r;
        int p = l - 1, q = r;
        int v = arr[r].dados.avaliacao;

        while (true) {
            while (arr[++i].dados.avaliacao < v)
                ;

            while (v < arr[--j].dados.avaliacao)
                if (j == l)
                    break;

            if (i >= j)
                break;

            ElementoAvaliacao temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;

            if (arr[i].dados.avaliacao == v) {
                p++;
                temp = arr[i];
                arr[i] = arr[p];
                arr[p] = temp;

            }

            if (arr[j].dados.avaliacao == v) {
                q--;
                temp = arr[q];
                arr[q] = arr[j];
                arr[j] = temp;
            }
        }

        ElementoAvaliacao temp = arr[i];
        arr[i] = arr[r];
        arr[r] = temp;

        j = i - 1;
        for (int k = l; k < p; k++, j--) {
            temp = arr[k];
            arr[k] = arr[j];
            arr[j] = temp;
        }

        i = i + 1;
        for (int k = r - 1; k > q; k--, i++) {
            temp = arr[i];
            arr[i] = arr[k];
            arr[k] = temp;
        }
    }

    static void sort(ElementoAvaliacao[] arr, int l, int r) {
        if (r <= l)
            return;

        i = 0;
        j = 0;

        partition(arr, l, r);

        sort(arr, l, j);
        sort(arr, i, r);
    }

}
