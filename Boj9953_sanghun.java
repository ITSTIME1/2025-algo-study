package org.example;

import java.util.Scanner;

/**
 * 백준 9953
 *
 * <p>
 * 문제 : <br>
 * 상근이는 문자열에 폭발 문자열을 심어 놓았다. 폭발 문자열이 폭발하면 그 문자는 문자열에서 사라지며, 남은 문자열은 합쳐지게 된다.
 * 폭발은 다음과 같은 과정으로 진행된다.
 * 문자열이 폭발 문자열을 포함하고 있는 경우에, 모든 폭발 문자열이 폭발하게 된다. 남은 문자열을 순서대로 이어 붙여 새로운 문자열을 만든다.
 * 새로 생긴 문자열에 폭발 문자열이 포함되어 있을 수도 있다.
 * 폭발은 폭발 문자열이 문자열에 없을 때까지 계속된다.
 * 상근이는 모든 폭발이 끝난 후에 어떤 문자열이 남는지 구해보려고 한다. 남아있는 문자가 없는 경우가 있다. 이때는 "FRULA"를 출력한다.
 * 폭발 문자열은 같은 문자를 두 개 이상 포함하지 않는다.
 * </p>
 *
 * <p>
 * 입력 : <br>
 * 첫째 줄에 문자열이 주어진다. 문자열의 길이는 1보다 크거나 같고, 1,000,000보다 작거나 같다.
 * 둘째 줄에 폭발 문자열이 주어진다. 길이는 1보다 크거나 같고, 36보다 작거나 같다.
 * 두 문자열은 모두 알파벳 소문자와 대문자, 숫자 0, 1, ..., 9로만 이루어져 있다.
 * </p>
 *
 * <p>
 * 출력 : <br>
 * 첫째 줄에 모든 폭발이 끝난 후 남은 문자열을 출력한다.
 * </p>
 */
public class Boj9953_sanghun {
    /*
     * < 풀이과정 >
     * [주안점 1 - 폭발 횟수]
     * 반복 작업이 얼마나 적은지가 관건입니다. 한번의 작업에서 폭발시킬 수 있는 문자는 전부 폭발 시킬 수 있도록 하려했으나,
     * 컴퓨터 시점에서 봤을 때 순차처리 되기 때문에 폭발 작업 횟수는 줄일 수 없어보입니다.
     *
     * [주안점 2 - 2차 폭발]
     * 문자가 폭발하면서 폭발 문자가 생길 수 있습니다. 처음에는 문자열을 순회할 생각을 하였으나,
     * 반복적으로 문자열을 순회하는 것을 줄일 수 없을까 고민했으나, 답을 찾기 전에 메모리가 터졌습니다.
     *
     * [주안점 3 - 메모리]
     * 처음에 문자열을 넣어 두고 삭제시키는 방향으로 수행하려 하였으나, String는 불변형이기에 매번 replace하면서 String객체가 생성됩니다.
     * 따라서 String보다 메모리 측면에서 효율이 좋은 가변형인 StringBuilder를 이용해보기로 하였습니다.
     * 기존엔 문자열 전체를 저장해두고 지우는 방식을 사용하였으나, 애시당초 빌더처럼 결과 문자열을 쌓아가면서 맨 끝에 폭발 문자열만 지우면
     * 중간에 지울 필요도 없고 2차 폭발에 대해 염두할 필요가 없어 해당 방법으로 수행했습니다. 사실 그냥 쌓아보자 생각만했는데,
     * 구현하면서 보니까 2차 폭발도 자동적으로 해결되더라구요 개꿀
     * 검색의 힘을 빌렸던 부분이 subSequence 부분인데 subString은 보니까 내부적으로 String 객체를 생성해 반환하더라구요
     * 메모리 측면에서 아쉬워서 대안을 찾다가 subSequence 로 대체했습니다.
     * 객체 반환이 아니라 StringBuilder를 참조하는 view를 반환하는 메소드로 효율을 조금 높혔습니다.
     *
     * [1번째 시도 - 실패]
     * 한번의 작업에서 폭발시킬 수 있는 문자는 전부 폭발시킬 수 있도록 replace 메소드를 사용했습니다.
     * 반복은 contains로 포함되지 않을 때까지 진행하도록 하였습니다.
     * 메모리가 부족했습니다. 젠장
     *
     * [2번째 시도 - 성공]
     * 위 주안점 대로 수행했습니다.
     *
     */
    public static void main(String[] args) {
        // 폭발 문자열 저장
        Scanner scanner = new Scanner(System.in);
        String startStr = scanner.nextLine();
        String boomStr = scanner.nextLine();

        // 게임 시작
        Boj9953_sanghun_problem problem = new Boj9953_sanghun_problem(startStr, boomStr);
        problem.boom();
    }
    static class Boj9953_sanghun_problem {
        String startStr;
        String boomStr;
        int boomStrLength;
        String emptyStr = "FRULA";
        StringBuilder resultStr = new StringBuilder();
        String finalStr;

        // 기본 생성자
        Boj9953_sanghun_problem() {};

        // 변수 초기화
        Boj9953_sanghun_problem(String startStr, String boomStr) {
            this.startStr = startStr;
            this.boomStr = boomStr;
            this.boomStrLength = boomStr.length();
        };

        // 게임 시작 메소드 
        void boom() {
            for (int i=0; i<startStr.length(); i++) {
                resultStr.append(startStr.charAt(i));

                if(resultStr.length() >= boomStrLength && resultStr.subSequence(resultStr.length() - boomStrLength, resultStr.length()).equals(boomStr)) {
                    resultStr.delete(resultStr.length() - boomStrLength, resultStr.length());
                }
            }

            finalStr = resultStr.toString().isEmpty() ? emptyStr : resultStr.toString(); // Empty Check

            System.out.println(finalStr);
        }
    }
}
