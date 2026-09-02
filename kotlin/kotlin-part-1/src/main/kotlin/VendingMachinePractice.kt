
fun printMenu1(totalMoney: Int) {
    println("================== 자판기 ==================")
    println("[1]콜라 : $COKE, [2]사이다 : $CIDER, [3]환타 : $FANTA, [4]물 : $WATER, [5]돈 넣기, [6]종료")
    println("현재 금액 : $totalMoney")
    println("============================================")
}

fun getChoice1():Int {
    println("원하는 메뉴를 선택하시오.")

    return readln().toInt()
}

fun getMoney1(): Int {
    println("돈을 넣으시오.")

    return readln().toInt()
}

fun calcMoney1(totalMoney: Int, price: Int): Int {
    return totalMoney - price
}

fun calcException1() {
    println("잔액이 부족합니다.")
}

fun main(){
    var totalMoney = 0

    while (true) {
        printMenu1(totalMoney)

        val choice = getChoice1()
        var result = -1;

        when (choice) {
            1 -> {
                result = calcMoney1(totalMoney, COKE)
                if (result < 0) {
                    calcMoneyException()
                } else {
                    totalMoney = result
                    println("콜라가 나왔습니다.")
                }
            }

            2 -> {
                result = calcMoney1(totalMoney, CIDER)
                if (result < 0) {
                    calcMoneyException()
                } else {
                    totalMoney = result
                    println("사이다가 나왔습니다.")
                }
            }

            3 -> {
                result = calcMoney1(totalMoney, FANTA)
                if (result < 0) {
                    calcMoneyException()
                } else {
                    totalMoney = result
                    println("환타가 나왔습니다.")
                }
            }

            4 -> {
                result = calcMoney1(totalMoney, WATER)
                if (result < 0) {
                    calcMoneyException()
                } else {
                    totalMoney = result
                    println("물이 나왔습니다.")
                }
            }

            5 -> totalMoney += getMoney()
            6 -> {
                println("\n 잔돈 ${totalMoney}원이 반환되었습니다.")
                return
            } else -> println("잘못 입력하셨습니다. 다시 입력해주세요.")
        }

    }
}