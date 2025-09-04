// class Boj9935 {
// 	public static void main(String[] args) throws IOException{
// 		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

// 		String input = br.readLine();
// 		String target = br.readLine();

// 		Stack<Character> first_stack = new Stack<>();
// 		Stack<Character> second_stack = new Stack<>();

// 		int target_len = target.length();

// 		int index = 0;
// 		for (int i = 0; i < input.length(); i++) {
// 			// 만약 charAt 문자가 target 에 포함되어 있는 경우
// 			if(target.indexOf(input.charAt(i)) != -1) {
// 				second_stack.push(input.charAt(i));
// 				// 만약 stack 의 길이가 target 의 길이와 같은 경우	
// 				if(second_stack.size() == target_len) {
// 					List<Character> subString = stack.subList(index, second_stack.size() - index + 1);
// 					if(subString.toString().equals(target)) {
// 						second_stack.clear()
// 						if(index - 1 == 0 ) {
// 							index = 0; 
// 						} else {
// 							index -= 1;
// 						}
// 					} else {
// 						index+=1;
// 					}
// 				}
// 			} else {								
// 				// 만약 charAt 문자가 target 에 포함되어 있지 않은 경우
// 				first_stack.push(input.charAt(i));
// 			}
// 		}

// 		if(first_stack.size() == 0 && second_stack.size() == 0) {
// 			System.out.println("FRULA");
// 		} else if (first_stack.size() == 0 && second_stack.size() != 0 ) {
// 			List<Character> sub = second_stack.subList(second_stack.size());
// 			System.out.println(sub);
// 		} else if(first_stack.size() != 0 && second_stack.size() == 0) {
// 			List<Character> sub = first_stack.subList(first_stack.size());
// 			System.out.println(sub);
// 		} else {
// 			List<Character> sub = first_stack.subList(first_stack.size());
// 			List<Character> sub1 = second_stack.subList(second_stack.size());
// 			System.out.println(sub + sub1);
// 		}

// 	}
// }
import java.io.*;
import java.util.*;

public class Boj9935 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();
        String target = br.readLine();

        Stack<Character> st = new Stack<>();

        int targetLen = target.length();
        int endTargetChar = target.charAt(targetLen - 1);
        
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            st.push(ch);
            // if (ch == endTargetChar && st.size() >= targetLen) {
            //     boolean match = true;
            //     int index = 0;
            //     while(index < targetLen) {

            //     	int offset = st.size() - targetLen + index;
            //     	if(st.get(offset) != target.charAt(index)) {
            //     		match = false;
            //     		break;
            //     	}
            //     	index++;
            //     }

            //     if (match) {
            //         for (int j = 0; j < targetLen; j++) {
            //             st.pop();
            //         }
            //     } else continue;
            // }
            if(st.size() >= targetLen) {
            	boolean match = true;
            	int offset = targetLen;
            	for (int j = 0; j < targetLen; j++) {
        			// 스택의 top에서 i번째 떨어진 문자
        			char stackChar = st.get(st.size() - 1 - j);
        			// target 문자열의 뒤에서 i번째 문자
        			char targetChar = target.charAt(targetLen - 1 - j);
			
        			if (stackChar != targetChar) {
        			    match = false;
        			    break;
        			}
    			}
    			if (match) {
    			    int cnt = 0;
    			    while (cnt < targetLen) {
    			        st.pop();
    			        cnt++;
    			    }
    			}
    		}
        }

        StringBuilder result = new StringBuilder();
        for (char ch : st) {
            result.append(ch);
        }

        System.out.println(result.length() == 0 ? "FRULA" : result.toString());
    }
}
