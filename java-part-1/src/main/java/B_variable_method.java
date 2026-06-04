public class B_variable_method {

    // * 함수 : 프로그래밍에서 특정 작업을 수행하기 위해 작성된 코드의 묶음
    /*
        1. 함수 선언 : 함수의 이름과 특성을 정의하는 부분
        2. 매개 변수(파라미터) : 매개변수는 함수가 작업을 수행하는 데 필요한 입력값을 전달받는 부분이다.
        3. 반환 타입 : 함수가 어떤 유형의 값을 반환할지를 정의
     */
    public static void exam1() {
        // 수행할 내용
        byte myByte = 127;
        System.out.println("myByte : " + myByte);

        short myShort = 32767;
        System.out.println("myShort : " + myShort);

        int age= 20;
        System.out.println("age : " + age);

        int num1 = 100;
        System.out.println("num1 : " + num1);
        num1 = 200;
        System.out.println("num1 : " + num1);
        num1 = 300 + 200;
        System.out.println("num1 : " + num1);

        long myLong = 1234567890123456789L;
        System.out.println("myLong : " + myLong);

        char myChar = 'a';
        System.out.println("myChar : " + myChar);
        myChar = 66;
        System.out.println("myChar : " + myChar);

        boolean myBoolean = true;
        System.out.println("myBoolean : " + myBoolean);
        myBoolean = false;
        System.out.println("myBoolean : " + myBoolean);

        // 상수
        final float PI = 3.14f;
        System.out.println("PI : " + PI);
        final double LONG_PI = 3.1415926535;
        System.out.println("LONG_PI : " + LONG_PI);

        // 문자열 -> 참조형
        String str = "Hello World";
        System.out.println("str : " + str);
    }

    public static int add(int a, int b) {
//        int result = a + b;
//        return result;
        return a + b;
    }

    // minus
    // 20, 10 -> 정수반환
    public static int minus(int a, int b) {
        return a - b;
    }

    // 결과만 출력하는 함수 printResult를 완성해주세요
    public static void printResult(int result) {
        System.out.println("result : " + result);
        return;
        /*
         * return
         * 1. 값을 반환 : 함수가 어떤 값을 계산하거나 처리한 후, 그 결과를 호출한 코드로 돌려줄 때 사용
         * 2. 함수 종료 : return 문이 실행되면 행당 함수는 즉시 종료된다.
         */
    }


    static void main(String[] args) {
        int result = add(10, 20);
        printResult(result);
        result = minus(20, 10);
        printResult(result);

    }
}