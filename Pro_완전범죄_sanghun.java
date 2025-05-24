package org.example;


import java.util.Stack;

/**
 * 프로그래머스 2025 프로그래머스 코드챌린지 2차 예선 - 완전범죄
 *
 * <p>
 * 문제 : <br>
 * A도둑과 B도둑이 팀을 이루어 모든 물건을 훔치려고 합니다. 단, 각 도둑이 물건을 훔칠 때 남기는 흔적이 누적되면 경찰에 붙잡히기 때문에, 두 도둑 중 누구도 경찰에 붙잡히지 않도록 흔적을 최소화해야 합니다.
 *
 * 물건을 훔칠 때 조건은 아래와 같습니다.
 *
 * 물건 i를 훔칠 때,
 * A도둑이 훔치면 info[i][0]개의 A에 대한 흔적을 남깁니다.
 * B도둑이 훔치면 info[i][1]개의 B에 대한 흔적을 남깁니다.
 * 각 물건에 대해 A도둑과 B도둑이 남기는 흔적의 개수는 1 이상 3 이하입니다.
 * 경찰에 붙잡히는 조건은 아래와 같습니다.
 *
 * A도둑은 자신이 남긴 흔적의 누적 개수가 n개 이상이면 경찰에 붙잡힙니다.
 * B도둑은 자신이 남긴 흔적의 누적 개수가 m개 이상이면 경찰에 붙잡힙니다.
 * 각 물건을 훔칠 때 생기는 흔적에 대한 정보를 담은 2차원 정수 배열 info, A도둑이 경찰에 붙잡히는 최소 흔적 개수를 나타내는 정수 n, B도둑이 경찰에 붙잡히는 최소 흔적 개수를 나타내는 정수 m이 매개변수로 주어집니다. 두 도둑 모두 경찰에 붙잡히지 않도록 모든 물건을 훔쳤을 때, A도둑이 남긴 흔적의 누적 개수의 최솟값을 return 하도록 solution 함수를 완성해 주세요. 만약 어떠한 방법으로도 두 도둑 모두 경찰에 붙잡히지 않게 할 수 없다면 -1을 return해 주세요.
 *
 * 제한사항
 * 1 ≤ info의 길이 ≤ 40
 * info[i]는 물건 i를 훔칠 때 생기는 흔적의 개수를 나타내며, [A에 대한 흔적 개수, B에 대한 흔적 개수]의 형태입니다.
 *         1 ≤ 흔적 개수 ≤ 3
 *         1 ≤ n ≤ 120
 *         1 ≤ m ≤ 120
 * </p>
 *
 */

public class Pro_완전범죄_sanghun {
    /*
     * < 풀이과정 >
     * [주안점 1 - 탐색]
     * 모든 경우의 수를 확인하지 않고 정답 도출이 가능한가? 일단 방법이 안 떠오름
     * 모든 경우의 수를 확인해가며 확인해가며 검증 시도
     *
     * [주안점 2 - 자료구조]
     * 경우의 수를 넣어가며 후입 선출로 저장 및 판단해야 되는데, 리스트로 할까 하다가 stack 한 번 써보자 싶어서 써봤습니다.
     * 
     * [주안점 3 - 반복 줄이기]
     * 모든 경우의 수를 확일 할 때 누군가 경찰에 붙잡히는 경우 이후 경우의 수는 stack에 추가하지 않음
     *
     * [주안점 4 - 가능한 경우의 수가 없는 경우]
     * min이 한번도 수행 안된 경우 -1로 넘겨줘야 되기에 모든 경우의 후 확인 후 최소값이 초기화 값인 경우 -1로 반환
     * 
     * 결국 시간 초과
     *
     */
    public static void main(String[] args) {
        // 조건
        int[][] info = {{1, 2}, {2, 3}, {2, 1}};
        int n = 4;
        int m = 4;

        // 경우의 수 확인
        int result = Pro_perfect_crime_sanghun_problem.solution(info, n, m);
        System.out.println(result);
    }
    static class Pro_perfect_crime_sanghun_problem {
        public static int solution(int[][] info, int n, int m) {
            Stack<int[]> stack = new Stack<>();
            stack.push(new int[]{0, 0, 0}); // 경우의 수 시작 { 현재 물건 인덱스, A 흔적 합, B 흔적 합 }

            int aTraceMin = info.length * 3 + 1; // A의 최소 흔적 개수 | 최대 경우의 수로 초기화 ( 물건 * 최대 흔적 3 )

            // 경우의 수 추가
            while (!stack.isEmpty()) {
                int[] status = stack.pop();
                int idx = status[0];
                int aTrace = status[1];
                int bTrace = status[2];

                // 모든 물건 훔치고 A 흔적 최솟값 갱신
                if (idx == info.length) {
                    if (aTrace < n && bTrace < m) { // 조건 만족할 때만
                        aTraceMin = Math.min(aTraceMin, aTrace);
                    }
                    
                    continue;
                }

                // 경찰에 붙잡히면 경우의 수 추가 안함
                if (aTrace >= n || bTrace >= m) continue;
                
                // A가 훔치는 경우 추가
                stack.push(new int[]{idx + 1, aTrace + info[idx][0], bTrace});

                // B가 훔치는 경우 추가
                stack.push(new int[]{idx + 1, aTrace, bTrace + info[idx][1]});
            }

            // 물건 못 훔친 경우 -1 판단
            return aTraceMin == info.length * 3 + 1 ? -1 : aTraceMin;
        }
    }
}
