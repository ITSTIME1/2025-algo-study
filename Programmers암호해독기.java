import java.util.*;
class Solution {
    public int solution(int n, int[][] q, int[] ans) {

        // 제외해야 할 숫자들 (ans[i] == 0 인 경우)
        // ans[i] 가 0 이라는 것은, 시스템응답이 0이고, 그런 경우는, 해당 정수집합을 입력 했을때
        // 맞지 않았다는 거니까, 비교 하지 않아도 됨.
        // 따라서, 비교하지 않아도 되는 숫자들은 걸러냄.
        Set<Integer> mustExclude = new HashSet<>();
        for (int i = 0; i < q.length; i++) {
            if (ans[i] == 0) {
                for (int num : q[i]) {
                    mustExclude.add(num);
                }
            }
        }

        // 제외된 숫자를 뺀 후보군 생성
        List<Integer> candidates = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (!mustExclude.contains(i)) {
                candidates.add(i);
            }
        }

        // 조합 생성
        List<List<Integer>> result = new ArrayList<>();
        generateCombinations(candidates, 0, new ArrayList<>(), result);


        int answer = 0;
        int l = result.size();
        while (l -- > 0) {
            List<Integer> tg = result.get(l);
            BitSet bs = new BitSet();
            for (Integer i  : tg) {
                bs.set(i);
            }

            // ans 매칭 확인
            Boolean isAllMatch = true;

            // q[i] 를 가지고 옴
            for (int i = 0; i < q.length; i++) {
                int[] query = q[i];
                BitSet compare = new BitSet();
                for (int num : query) {
                    compare.set(num);
                }   

                BitSet clone = (BitSet) bs.clone();
                clone.and(compare);
                // 교집합 개수
                int count = clone.cardinality();

                if(ans[i] != count) {
                    isAllMatch = false;
                    break;
                }
            }

            if(isAllMatch) {
                answer = answer + 1;
            }

        }        
        return answer;

    }
    public static void generateCombinations(List<Integer> pool, int start, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == 5) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < pool.size(); i++) {
            current.add(pool.get(i));
            generateCombinations(pool, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

}
