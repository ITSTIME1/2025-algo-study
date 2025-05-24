package org.example;

import java.util.HashSet;
import java.util.Set;

public class Pro_비밀코드해독_sanghun {
}

class Solution {
    private int validCaseCount = 0; // 가능한 조합 개수

    // n - 숫자 범위
    // q - 범위 내에서 5개의 서로 다른 오름차순 숫자 조합 배열
    // ans - 각 조합 유효 숫자 개수 배열
    public int solution(int n, int[][] q, int[] ans) {
        // 주어진 조합을 InputCase 객체 배열로 저장
        InputCase[] inputCases = new InputCase[q.length];
        for (int i = 0; i < q.length; i++) {
            inputCases[i] = new InputCase(q[i], ans[i]);
        }

        // 가능한 조합 생성 및 유효성 검사
        createAndCheckCase(n, 1, new HashSet<>(), inputCases);

        return validCaseCount;
    }

    // 백트래킹 - 모든 조합 생성 및 유효성 검사
    void createAndCheckCase(int n, int currentInt, Set<Integer> currentSet, InputCase[] inputCases) {
        // 조합 완성 시 유효성 검사
        if (currentSet.size() == 5) {
            if (isValidCase(currentSet, inputCases)) {
                validCaseCount++;
            }
            return;
        }

        for (int i = currentInt; i <= n; i++) {
            currentSet.add(i);
            createAndCheckCase(n, i + 1, currentSet, inputCases);
            currentSet.remove(i);
        }
    }

    // 완성된 조합 유효성 검사 ( Inputcase와 비교 )
    boolean isValidCase(Set<Integer> currentSet, InputCase[] inputCases) {
        for (InputCase inputCase : inputCases) {
            if (!inputCase.isValid(currentSet)) {
                return false;
            }
        }
        return true;
    }
}

// 주어진 조합 클래스
class InputCase {
    private final Set<Integer> inputSet; // 조합
    private final int validCount; // 유효 숫자 개수

    public InputCase(int[] input, int validCount) {
        this.inputSet = new HashSet<>();
        for (int num : input) {
            inputSet.add(num);
        }
        this.validCount = validCount;
    }

    // 조합 유효 숫자 개수 비교
    public boolean isValid(Set<Integer> currentSet) {
        int count = 0;
        for (int num : inputSet) {
            if (currentSet.contains(num)) count++;
        }
        return count == validCount;
    }
}