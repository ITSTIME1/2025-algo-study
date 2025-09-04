import java.util.*;

class Solution {

    static class Point {
        int r;
        int c;

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return r == point.r && c == point.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
      
    }


    public int solution(int[][] points, int[][] routes) {

        Map<Integer, Point> pointMap = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            pointMap.put(i + 1, new Point(points[i][0], points[i][1]));
        }

        List<Map<Integer, Point>> robotPaths = new ArrayList<>();
        int maxOverallTime = 0;

        for (int robotId = 0; robotId < routes.length; robotId++) {
            Map<Integer, Point> currentRobotPath = new HashMap<>();
            int timeElapsed = 0; // 현재 로봇의 이동 시간

            Point startPoint = pointMap.get(routes[robotId][0]);
            currentRobotPath.put(timeElapsed, startPoint); 
            
            Point currentPos = startPoint;

            for (int i = 0; i < routes[robotId].length - 1; i++) {
                Point endSegment = pointMap.get(routes[robotId][i+1]);

                int r = currentPos.r;
                int c = currentPos.c;

                // r 우선 이동
                while (r != endSegment.r) {
                    r += (endSegment.r > r) ? 1 : -1;
                    timeElapsed++;
                    currentRobotPath.put(timeElapsed, new Point(r, c));
                }
                
                // c 이동 (r 이동이 끝난 후)
                while (c != endSegment.c) {
                    c += (endSegment.c > c) ? 1 : -1;
                    timeElapsed++;
                    currentRobotPath.put(timeElapsed, new Point(r, c));
                }
                currentPos = new Point(r, c); 
            }
            robotPaths.add(currentRobotPath);
            maxOverallTime = Math.max(maxOverallTime, timeElapsed); 
        }

        // 3. 충돌 감지
        int totalDangerousSituations = 0;
        for (int t = 0; t <= maxOverallTime; t++) {
            Map<Point, Set<Integer>> robotsAtCurrentTime = new HashMap<>();
            
            for (int robotId = 0; robotId < robotPaths.size(); robotId++) {
                Map<Integer, Point> currentRobotPath = robotPaths.get(robotId);
                
                if (currentRobotPath.containsKey(t)) { 
                    Point currentPos = currentRobotPath.get(t);
                    robotsAtCurrentTime.computeIfAbsent(currentPos, k -> new HashSet<>()).add(robotId);
                }
            }

            // 현재 시간 t에서 두 대 이상의 로봇이 있는 좌표 확인
            for (Set<Integer> robotIdsAtCoord : robotsAtCurrentTime.values()) {
                if (robotIdsAtCoord.size() >= 2) {
                    totalDangerousSituations++;
                }
            }
        }

        return totalDangerousSituations;
    }
}