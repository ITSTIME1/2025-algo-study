import java.io.*;
import java.util.ArrayList;

class Boj12873{
    public static void main(String[] args) throws IOException {
        //입력을 받는다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        //1,2,3...사람을 리스트에 집어 넣는다.
        ArrayList<Integer> participants = new ArrayList<Integer>();
        for(int num = 1; num < N+1 ; num++){
            participants.add(num);
        }


        //필요하다고 생각하는 변수 : 인원 수, 현재 인덱스
        int p_num = N;
        Long current_index = 0L;

        //몇 번인 사람을 고르는게 아닌, 몇 번째 사람을 고르는 것이기때문에, 인덱스로 처리한다.
        //인덱스를 결정하는 방법으로, 지금 현재 인덱스와 t^3번째를 더한 값을 남은 인원수로 나누게 되면 값이 나온다.
        //쉽게 알아보자면면 t^3값을 현재 인원 수로 나누게 되면, 백준이가 움직여야 할 횟수가 나온다.
        //그러나 백준이는 원형으로 앉아있는 사람들을 돌고 있기 때문에 최대 인원 수 만큼 오른쪽을 가면, 결국 그 다음은 처음이 된다.
        //그러므로 t^3 + 현재 인덱스를 해서 나눠주면, 결과적으로 도달할 인덱스의 값이 나온다.
        //하지만, 인덱스는 0부터 시작이므로 -1해서 관리해주면 된다.
        for(int t = 1; t < N; t++){
            
            //최대치인 5000을 넣었다는 가정에서, t^3는 4999^3까지 늘어난다. 그러면 124,925,014,999이 되는데, int의 최댓값인 2,147,483,647를 넘어서게 된다.
            //다루는 형식을 Long으로 전체적으로 바꿔줘야만한한다.
            Long t_value = (long) Math.pow(t, 3);

            current_index = ((t_value+current_index)%p_num)-1;
            p_num -= 1;

            //파이썬은 -1 인덱스 찾아가면 마지막으로 가는데 자바는 안되네...?
            if(current_index == -1){
                current_index = (long)p_num;
            }
            participants.remove(current_index.intValue());
        }
        System.out.println(participants.get(0));
    }
}