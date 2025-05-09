package gold3;

import java.io.*;
import java.util.*;

public class Boj22860 {
    public static void main(String[] args) throws IOException{
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String folder_count_line = br.readLine();
        String[] parts = folder_count_line.split(" ");

        int N = Integer.parseInt(parts[0]);
        int M = Integer.parseInt(parts[1]);

        //하위 폴더 이름을 DFS형식으로 탐색해서 파일 이름들을 Hash맵 형태로 넣어서 찾으면 파일 크기가 종류고, value를 다 더하면 파일 갯수일 것으로 생각하고 구현.
        //입력 받은 String을 그대로 리스트를 꺼내려면 다른 방법이 필요했음.
        //해시맵 형태로, <리스트 이름 : 리스트> 형식으로 구성.
        //각 리스트 형식은 [[파일 이름],[하위 폴더 이름]] 이런식으로 구성하려고함. (입력 받을때 파일은 0, 폴더는 1이므로 인덱스 접근이 수월하게 구성)
        Map<String, List<List<String>>> folder_map = new HashMap<>();

        for(int i = 0; i < M+N; i++){
            String file_or_folder = br.readLine();
            String[] ff_parts = file_or_folder.split(" ");

            //1이면 폴더로 정의하고, 폴더 리스트에 폴더를 넣는다.
            //계층 순으로 입력이 들어가지 않는다. 상당히 별로임.
            if (ff_parts[2].equals("1")) {
                // 상위 폴더에 하위 폴더 추가
                folder_map.putIfAbsent(ff_parts[0], Arrays.asList(new ArrayList<>(), new ArrayList<>()));
                folder_map.get(ff_parts[0]).get(1).add(ff_parts[1]);
            
                // 하위 폴더 초기화 (중복 방지)
                folder_map.putIfAbsent(ff_parts[1], Arrays.asList(new ArrayList<>(), new ArrayList<>()));
            } else {
                folder_map.putIfAbsent(ff_parts[0], Arrays.asList(new ArrayList<>(), new ArrayList<>()));
                folder_map.get(ff_parts[0]).get(0).add(ff_parts[1]);
            }           
            
        }

        int query_count = Integer.parseInt(br.readLine());
        for(int i = 0; i < query_count; i++){
            Map<String, Integer> total_files = new HashMap<>();
            
            // 슬래쉬 뒤 마지막 단어가 최종 목표 폴더이므로 잘라서 사용.
            String query = br.readLine();
            int lastSlash = query.lastIndexOf("/");
            String last = query.substring(lastSlash + 1);

            file_counting(last, folder_map, total_files);

            int file_count = sum_array(total_files.values());
            int file_type = total_files.size();

            System.out.printf("%d %d\n", file_type, file_count);

        }
    }
    
    //폴더를 하나씩 들어가면서 파일을 저장하고 폴더를 다시 재귀하는 형식으로 구현.
    public static Map<String, Integer> file_counting(String name, Map<String, List<List<String>>> folder_Hashmap, Map<String, Integer> total_files){
        
        List<String> files = folder_Hashmap.get(name).get(0);
        List<String> folders = folder_Hashmap.get(name).get(1);

        for (String folder : folders) {
            total_files = file_counting(folder, folder_Hashmap, total_files);
        }        

        for(String file : files){
            total_files.put(file, total_files.getOrDefault(file, 0) + 1);
        }

        return total_files;
    }
    
    public static int sum_array(Collection<Integer> list){
        int sum = 0;
        for(int num : list){
            sum += num;
        }
        return sum;
    }
}
