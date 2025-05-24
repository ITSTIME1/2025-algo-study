import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 백준 12873
 *
 * <p>
 * 문제 : <br>
 * 백준이는 BOJ 알고리즘 캠프 참가자 중 한 명에게 기념품을 주려고 한다. 하지만, 많은 참가자 중에서 어떤 사람을 뽑아서 기념품을 줘야하는지 고민이 되기 시작했다. 따라서, 백준이는 게임을 통해서 기념품을 받을 사람을 정하기로 결정했다.
 * 게임이 시작하기 전에 모든 참가자 N명은 원을 이루어서 앉아있다. 다음, 1부터 N까지 번호가 적혀있는 티셔츠를 시계방향으로 입는다. 이 티셔츠는 게임에 사용되지 않으며, 게임을 쉽게 하기 위해서 입는 티셔츠이다.
 * 게임은 단계로 이루어져 있으며, 첫 단계는 1단계이다. 각 단계가 시작될 때, 백준이는 어떤 참가자의 앞에 서있다. 그 다음, "하나"를 외친다. 그 다음, 시계 방향으로 다음 사람에게 이동하며 "둘"을 외친다. 이 과정은 t단계인 경우에 t**3을 외칠 때 까지 진행한다. 예를 들어, 1단계에서는 1까지 외치며, 2단계에서는 8까지, 3단계에서는 27까지 외친다.
 * 각 단계가 끝난 경우에, 백준이가 앞에 서 있는 사람은 게임에서 제외된다. (t단계인 경우에 t3을 외칠 때 앞에 있던 사람) 사람이 제거된 후에는 백준이는 시계 방향으로 다음 사람에게 이동한다. 1단계에서 백준이는 티셔츠 1번을 입고 있는 사람의 앞에 있다. 게임은 원에 한 명이 남을 때 까지 진행되며, 마지막 남은 사람이 기념품을 가져가게 된다.
 * 참가자의 수 N이 주어졌을 때, 어떤 티셔츠를 입고 있는 사람이 기념품을 받는지 구하는 프로그램을 작성하시오.
 * </p>
 *
 * <p>
 * 입력 : <br>
 * 첫째 줄에 BOJ 캠프 참가자의 수 N (1 ≤ N ≤ 5,000)이 주어진다.
 * </p>
 *
 * <p>
 * 출력 : <br>
 * 첫째 줄에 기념품을 받는 사람이 입고 있는 티셔츠의 번호를 출력한다.
 * </p>
 */
public class Boj12873_sanghun {
    /*
     * < 풀이과정 >
     * [주안점 1 - 계산식]
     * 게임 진행 방식 그대로 전부 수행할 수 없음. 중간에 생략이 필요 -> % 연산자로 반복 줄이기
     * 1명이 될 때까지 수행하면 됨
     * 수행하는 계산식은 '{(현재 라운드 값 세제곱) + (직전 라운드 종료 위치) - 1(배열은 0부터 시작이므로 조정)} % (현재 남은 사람 수)' 으로 삭제할 사람 index를 도출
     *
     *
     * [주안점 2 - 자료구조 선택]
     * 담는 사람을 리스트로 할 것이냐 큐로할 것이냐인데, 중간 요소 제거가 빈번하게 발생하니 리스트로
     *
     * ArrayList는 요소 찾는 게 O(1), remove 후 당기는 게 O(n)
     * LinkedList는 요소 찾는 게 O(n), remove 가 O(1)
     *
     * 수행 시간 테스트
     * 결론: 숫자가 커질 수록 Array가 더 나음
     * 이유: 처음 요소를 넣는 시간도 배열 구조인 Array가 더 빠르며,
     *      매번 인덱스를 찾아서 삭제해야 하므로 인덱스 접근이 느린 Linked가 불리
     *      참조 구조로 메모리도 더 많이 쓸텐데.. 여러모도 Array가 더 나은 듯
     *
     * task1: 7명
     * Array: 49000ns | 41000ns | 42600ns
     * Linked: 37700ns | 40300ns | 46800ns
     *
     * task2: 1776명
     * Array: 1075700ns | 1137200ns | 843600ns
     * Linked: 2529100ns | 2075700ns | 2058900ns
     *
     * task3: 2555명
     * Array: 1551300ns | 1534500ns | 1560400ns
     * Linked: 3801000ns | 4027200ns | 4204700ns
     *
     * task5: 4998명
     * Array: 2003700ns | 2569700ns | 2649700ns
     * Linked: 11651300ns | 11720000ns | 11504500ns
     *
     */
    public static void main(String[] args) {
        // 게임 참가자 수 입력
        Scanner scanner = new Scanner(System.in);
        int startPersonCnt = scanner.nextInt();

        // 게임 세팅 및 시작
        Boj12873_sanghun_game game = new Boj12873_sanghun_game(startPersonCnt);
        game.start();
    }
    static class Boj12873_sanghun_game {
        long startPersonCnt = 0; // 게임 참여자 수
        long nowPersonCnt = 0; // 현재 남은 사람
        long round = 1; // 현재 라운드
        int lastIndex = 0; // 현재 위치
        List<Integer> personList = new ArrayList<Integer>(); // 남은 사람 List

        Boj12873_sanghun_game() {};

        // 게임 세팅 및 초기화

        Boj12873_sanghun_game(int startPersonCnt) {
            this.startPersonCnt = startPersonCnt;
            this.nowPersonCnt = startPersonCnt;

            for ( int i = 1 ; i <= startPersonCnt; i++) {
                personList.add(i);
            }
        };

        // 게임 시작 메소드 
        void start() {
            for ( ; personList.size() > 1; round++, nowPersonCnt-- ) {
                lastIndex = (int)( (Math.pow(round, 3) + lastIndex - 1 ) % nowPersonCnt); // {(현재 라운드 값 세제곱) + (직전 라운드 종료 위치) - 1(배열은 0부터 시작이므로 조정)} % (현재 남은 사람 수)
                personList.remove(lastIndex);
            }

            System.out.print(personList.get(0));
        }
    }
}
