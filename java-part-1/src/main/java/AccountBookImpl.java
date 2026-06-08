import java.util.*;

public class AccountBookImpl implements AccountBook{
    private Map<String, List<Item>> data = new HashMap<>();
    private Scanner sc = new Scanner(System.in);
    @Override
    public void addAccount() {
        System.out.println("날짜 입력 :");
        String date = sc.nextLine();
        // 인텔리제이에서 추천해준 방법
        // List<Item> itemList = data.computeIfAbsent(date, k -> new ArrayList<>());

        List<Item> itemList = data.get(date);
        if(itemList == null){
            itemList = new ArrayList<>();
            data.put(date, itemList);
        }

        String repeat = "y";
        while(repeat.equals("y")) {
            System.out.println("물품 명 입력 :");
            String itemName = sc.nextLine();
            System.out.println("물품 가격 입력 :");
            int price = 0;

            while(true){
                String input = sc.nextLine().trim();
                if(input.matches("\\d+")){
                    price = Integer.parseInt(input);
                    break;
                }else{
                    System.out.println("숫자를 입력해주세요.");
                    continue;
                }
            }

            Item alreadyExistItem = null;
            for(Item item : itemList){
                if(item.getName().equals(itemName)){
                    alreadyExistItem = item;
                    break;
                }
            }

            if(alreadyExistItem != null){
                int oneTotalPrice = alreadyExistItem.getPrice() + price;
                alreadyExistItem.setPrice(oneTotalPrice);
            }else{
                Item newItem = new Item(itemName, price);
                itemList.add(newItem);

            }

            System.out.println("더 추가할까요? (y/n) > ");
            repeat = sc.nextLine().trim();
            if(repeat.equals("n")) break;
        }
        int totalPrice = 0;
        System.out.printf("[%s] 등록 완료\n", date);
        for(Item s : itemList){
            System.out.printf("%s : %d\n", s.getName(), s.getPrice());
            totalPrice += s.getPrice();
        }
        System.out.printf("합계 : %d\n\n", totalPrice);
    }

    @Override
    public void showAccount() {
        //데이터가 존재하지 않을 때 부분도 수정해야함 이렇게하면 데이터 안걸러짐
        if(data.isEmpty()){
            System.out.println("기록이 없습니다.");
            return;
        }
        List<String> dates = new ArrayList<>(data.keySet());
        Collections.sort(dates, Collections.reverseOrder());
        System.out.println("== 기록된 날짜 ==");
        for(String d : dates){
            System.out.println(d);
        }
        System.out.print("조회할 날짜 입력 > ");
        String date = sc.nextLine();

        if(data.containsKey(date)) {
            for(Item s :data.get(date)){
                System.out.printf("%s : %d원\n", s.getName(), s.getPrice());
            }
        }else{
            System.out.println("존재하지 않는 날짜입니다.");
            return;
        }
        System.out.printf("%s 조회 완료", date);
    }
    //해당 날짜가 존재하는지도 확인해야함
    @Override
    public void deleteAll() {
        if(data.isEmpty()){
            System.out.println("기록이 없습니다.");
            return;
        }
        List<String> dates = new ArrayList<>(data.keySet());
        Collections.sort(dates, Collections.reverseOrder());
        //== 기록된 날짜 ==2024-09-09 println 인데 왜 이리나옴?
        System.out.println("== 기록된 날짜 ==");
        for(String d : dates){
            System.out.println(d);
        }
        System.out.print("삭제할 날짜 입력 > ");
        String date = sc.nextLine();
        if(!data.containsKey(date)) {
            System.out.println("존재하지 않는 날짜입니다.");
            return;
        }
        data.remove(date);
        System.out.printf("%s 삭제 완료\n", date);
    }

    @Override
    public void deleteItem() {
        if(data.isEmpty()){
            System.out.println("기록이 없습니다.");
            return;
        }
        List<String> dates = new ArrayList<>(data.keySet());
        Collections.sort(dates, Collections.reverseOrder());
        System.out.println("== 기록된 날짜 ==");
        for(String d : dates){
            System.out.println(d);
        }

        //1. 날짜를 입력받고 존재하는지 확인
        System.out.print("조회할 날짜 입력 > ");
        String date = sc.nextLine();
        if(!data.containsKey(date)){
            System.out.println("해당 날짜는 존재하지 않습니다.");
            return;
        }

        //2.그 날의 항목들을 번호와 함께 출력한다.
        int i = 1;
        for(Item s :data.get(date)){
            System.out.printf("%d . %s : %d원\n\n", i++, s.getName(), s.getPrice());
        }

        //3. 삭제할 번호를 입력받는다.
        System.out.print("삭제할 항목 번호 입력 > ");
        int num = 0;
        while(true) {
            String input = sc.nextLine().trim();
            if(input.matches("\\d+")) {
                num = Integer.parseInt(input);
                break;
            } else {
                System.out.println("숫자를 입력해주세요.");
            }
        }

        //4. 번호 삭제
        data.get(date).remove(num - 1);

        //5. 그 날의 항목이 0개가 되면 날짜 자체도 지운다.
        if(data.get(date).isEmpty()) data.remove(date);
        System.out.printf("%s %s번 삭제 완료", date, num);
    }
}
