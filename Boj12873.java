import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Boj12873 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Integer N = sc.nextInt();
        if(!validate(N)) {
            return;
        }

        int current_index = 0;
        int pattern_len = N;
        long stage = 1;
        List<Integer> arr = new ArrayList<>();
        for(int i = 0; i < N; i++) {
            arr.add(i + 1);
        }


        // t=5000이면, t^3 == 1250억.. 근데 int 는 24억 정도..
        while (arr.size() > 1) {
            
            long offset = (stage * stage * stage) % pattern_len;
            
            if(offset == 0) {
                offset = pattern_len - 1;
            } else {
                offset -= 1;
            }
            int remove_index = (int)(current_index + offset) % pattern_len;
            arr.remove(remove_index);
            current_index = remove_index;
            pattern_len--;
            stage++;
        }
        System.out.println(arr.get(0));

    }
    private static boolean validate(Integer total) {
        if(total == null || (total < 0 || total > 5000)) {
            return false;
        }
        return true;
    }
}