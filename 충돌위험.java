import java.util.*;

class Solution {
    public int solution(int[][] points, int[][] routes) {
        int answer = 0;
        int oneRouteSize = routes[0].length;

        List<int[]> robotPosition = new LinkedList<>();

        // 초기화
        for (int i = 0; i < routes.length; i++) {
            int[] temp = points[routes[i][0] - 1];
            // [r, c, step, robot index]
            robotPosition.add(new int[] {temp[0], temp[1], 1, i});
        }
        answer += isDanger(robotPosition);

        // 시뮬레이션 시작
        while (!robotPosition.isEmpty()) {
            Iterator<int[]> iter = robotPosition.iterator();
            List<int[]> removeList = new ArrayList<>();

            while (iter.hasNext()) {
                int[] nowRobot = iter.next();
                int robotIndex = nowRobot[3];
                int endPoint = nowRobot[2];

                // 이동
                int[] moved = getRobotPosition(nowRobot, points[routes[robotIndex][endPoint] - 1]);

                nowRobot[0] = moved[0];
                nowRobot[1] = moved[1];

                // 도착 판정
                if (nowRobot[0] == points[routes[robotIndex][endPoint] - 1][0]
                        && nowRobot[1] == points[routes[robotIndex][endPoint] - 1][1]) {
                    if (endPoint == oneRouteSize - 1) {
                        // 도착 후 이번 턴까지 danger 포함
                        removeList.add(nowRobot);
                    } else {
                        // 다음 step 이동
                        nowRobot[2] = endPoint + 1;
                    }
                }
            }

            // 위험 상황 체크
            answer += isDanger(robotPosition);

            // 도착한 로봇 제거
            robotPosition.removeAll(removeList);
        }

        return answer;
    }

    public int[] getRobotPosition(int[] nowPosition, int[] endPosition) {
        int difRow = endPosition[0] - nowPosition[0];
        int difCol = endPosition[1] - nowPosition[1];

        if (nowPosition[0] == endPosition[0] && nowPosition[1] == endPosition[1]) {
            return endPosition;
        }

        if (difRow != 0) {
            if (difRow > 0) {
                return new int[] {nowPosition[0] + 1, nowPosition[1], nowPosition[2]};
            } else {
                return new int[] {nowPosition[0] - 1, nowPosition[1], nowPosition[2]};
            }
        } else {
            if (difCol > 0) {
                return new int[] {nowPosition[0], nowPosition[1] + 1, nowPosition[2]};
            } else {
                return new int[] {nowPosition[0], nowPosition[1] - 1, nowPosition[2]};
            }
        }
    }

    public int isDanger(List<int[]> robotPosition) {
        Map<String, Integer> positionCount = new HashMap<>();
        int count = 0;

        // 좌표별 로봇 수 count
        for (int[] cur : robotPosition) {
            String key = cur[0] + " " + cur[1];
            positionCount.put(key, positionCount.getOrDefault(key, 0) + 1);
        }

        // 위험 상황: 칸 단위로 카운트
        for (int cnt : positionCount.values()) {
            if (cnt >= 2) {
                count += 1;  // 칸 당 위험 1건!
            }
        }

        return count;
    }
}