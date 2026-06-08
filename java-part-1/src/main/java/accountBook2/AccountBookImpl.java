package accountBook2;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class AccountBookImpl implements AccountBook{
    private final String DIR = "accountbook";
    private Scanner sc = new Scanner(System.in);

    public AccountBookImpl(){
        File folder = new File(DIR);
        if (!folder.exists()) folder.mkdir();
    }

    @Override
    public void addAccount() {
        String today = LocalDate.now().toString();
        File file = new File(DIR, today + ".txt");

        int total = 0;
        StringBuilder sb = new StringBuilder();
        String repeat = "y";
        int totalPrice = 0;
        while(repeat.equals("y")){
            //내역 추가
            System.out.print("항목 이름 > ");
            String itemName = sc.nextLine();
            System.out.print("금액 > ");
            int price = 0;
            String input = sc.nextLine();
            if(input.matches("\\d+")){
                price = Integer.parseInt(input);
            }else{
                System.out.println("숫자를 입력해주세요.");
                continue;
            }
            totalPrice += price;
            sb.append(itemName).append(" : ").append(price).append("원\n");
            //추가적으로 내역을 추가하는지에 대한 입력
            System.out.print("더 추가할까요 (y/n) > ");
            repeat = sc.nextLine().trim();

            /*추가 적용이 필요한 사항
            1. 같은 항목을 입력받는 경우 가격 더하기 : 추가하기 전에 파일 불러와서 항목들 배열에 넣고 있는지 없는지 체크해서 같은 항목 있으면 가격을 더해야하나
            2. 합계 새로 구하기 + 합계 항목이 항상 맨 아래로 오도록 하기
            */
        }
        sb.append("합계 : ").append(totalPrice).append("원\n");

        try(FileWriter fw = new FileWriter(file, true)){
            fw.write(sb.toString());
            System.out.println(file.getName() + " 에 저장 완료");
        } catch (IOException e) {
            System.out.println("저장 중 오류: " + e.getMessage());
        }
    }

    @Override
    public void showAccount() {
        File folder = new File(DIR);
        String[] files = folder.list();
        if(files == null){
            System.out.println("파일이 존재하지 않습니다.");
            return;
        }

        for(String name : files){
            if(name.endsWith(".txt")){
                System.out.println(name.replace(".txt", ""));
            }
        }
        System.out.print("조회 날짜 > ");
        //포맷 틀리는 경우 예외처리
        String day = sc.nextLine();
        File file = new File(DIR, day + ".txt");
        if(file.exists()){
            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                System.out.printf("===== %s 내역 =====\n", file.getName().replace(".txt", ""));
                while((line  = br.readLine()) != null){
                    System.out.println(line);
                }
            } catch (FileNotFoundException e) {
                System.out.println("해당 파일을 찾을 수 없습니다.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("===== 조회 완료 =====");
        }else{
            System.out.println("입력한 파일이 존재하지 않습니다.");
        }

    }

    @Override
    public void deleteAccount() {
        System.out.println("삭제 할 날짜를 입력해주세요.");
        File folder = new File(DIR);
        String[] files = folder.list();

        if(files == null){
            System.out.println("파일이 존재하지 않습니다.");
            return;
        }

        for (String name : files){
            if(name.endsWith(".txt")){
                System.out.println(name.replace(".txt",""));
            }
        }
        System.out.print("입력 > ");
        String inputDay = sc.nextLine().trim();
        File file = new File(DIR, inputDay + ".txt");
        if(file.exists()){
            if(file.delete()) System.out.printf("%s.txt 파일이 삭제되었습니다.", inputDay);
            else System.out.println("삭제 실패.");
        } else {
            System.out.println("입력한 파일이 존재하지 않습니다.");
        }
    }
}
