import java.io.*;


public class Boj9935 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //문자열, 폭발 문자열 입력
        String str = br.readLine();
        String boom_str = br.readLine();
        int boom_len = boom_str.length();        

        //폭발 문자열을 제거시키게된 후 다시 폭발 문자열이 생길 수 있다. c4를 지우는데, cc44이런식이면 한번 지우고도 c4가 남아서 한번 더 돌려야함.
        //그렇다면, 이전 문자열 길이와 처리 후 문자열길이가 같을 때까지 반복을 시키고자한다.

        /*한 번만 순회하면 되는 방법으로 찾으려고 한다.
          한글자씩 넣으면서, 만약 문자열 길이가 폭발 문자열보다 길어지는 순간부터 폭발 문자열이 있는지 확인 후, 있으면 지우면 된다.
          그런데, contain을 써서 확인하면, string을 다시 만들어서 사용하고, 아까보다 더 많은 연산이 필요하게 된다.
          Stringbuilder라는 걸 사용하면 빠르다고 한다. 그러면 contain같은게 없어서, 계속 끝부분을 비교해주는 식으로 구현해보고자 한다.
        */ 

        StringBuilder sb = new StringBuilder();

        for (char c : str.toCharArray()){
            sb.append(c);

            if(sb.length() >= boom_len){
                boolean is_boom = true;
                for(int i = 0 ; i < boom_len ; i++){
                    if (sb.charAt(sb.length() - boom_len + i) != boom_str.charAt(i)) {
                        is_boom = false;
                        break;
                    }
                }
                if(is_boom){
                    sb.delete(sb.length() - boom_len, sb.length());
                }
            }
        }

        //StringBuilder를 사용했으니까, 검사부분도 sb를 활용해 구현.
        if(sb.length()==0){
            System.out.println("FRULA");
        }else{
            System.out.println(sb);
        } 

        // //이 방법은 replace를 통해 문자열을 계속 생산하므로, 메모리초과가 남
        // while(pre_length != current_length){
        //     pre_length = str.length();
        //     str = str.replaceAll(boom_str, "");
        //     current_length = str.length();
        // }

        // //그 후, 문자열이 비었다면 FRULA, 아니라면 문자열을 그대로 출력한다.
        // if(str == ""){
        //     System.out.println("FRULA");
        // }
        // else{
        //     System.out.println(str);
        // }
    }    
}
