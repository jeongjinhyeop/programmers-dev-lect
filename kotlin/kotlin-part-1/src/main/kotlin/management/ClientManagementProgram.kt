package management


fun selectGrade(): Int {
    println("[요금제를 선택하세요]")
    println("[1]Lite : 10명 [2]Basic : 20명 [3]Premium : 30명")

    return readln().toInt()
}

fun printMenu(grade: Int): Int {
    println("[수행할 업무를 선택하세요 - 현재 회원수 :${userList.size}/${grade * 10}]")
    println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름) \n[4]회원전체조회 [5]회원정보 수정 [6]회원삭제 \n[7]프로그램 종료")
    return readln().toInt()
}

fun printName(): String {
    println("이름을 입력하세요.")
    return readln()
}

fun printEmail(): String {
    println("이메일을 입력하세요.")
    return readln()
}

fun printPhone(): String {
    println("연락처를 입력하세요.")
    return readln()
}

fun makeUser() {
    val name = printName()
    val email = printEmail()

    if (findUserByEmail(email) != null) {
        println("이미 등록된 이메일입니다.")
        return
    }

    val phone = printPhone()
    userList.add(User(name, email, phone))
    
    println("회원이 등록되었습니다.")
}

val userList = mutableListOf<User>()

fun findUserByEmail(email: String): User? {
    return userList.find { it.email == email }
}

fun findUserByName(userName: String): List<User> {
    return userList.filter { it.name == userName }
}

fun findAllUsers(): List<User> {
    return userList
}

fun updateUserByEmail(email: String) {
    val user = userList.find { it.email == email }

    if (user != null) {
        println("변경할 이름을 적어주세요")
        user.name = readln()
        println("변경할 이메일을 적어주세요")
        user.email = readln()
        println("변경할 연락처를 적어주세요")
        user.phone = readln()
    } else {
        println("해당 유저가 존재하지 않습니다.")
    }
}


fun deleteUserByEmail(email: String) {
    val user = userList.find { it.email == email }

    if (user != null) {
        userList.remove(user)
    } else {
        println("해당 유저가 존재하지 않습니다.")
    }
}

fun choiceMenu(grade : Int) {

    while(true) {
        val num = printMenu(grade)
        when (num) {
            1 -> {
                if (userList.size < grade * 10) {
                    makeUser()
                } else {
                    println("회원 등록 가능 인원을 초과했습니다.")
                }
            }
            2 -> {
                println("찾으실 분의 이메일을 입력해주세요")
                val user = findUserByEmail(readln())
                if (user != null) {
                    println("이름: ${user.name}, 이메일: ${user.email}, 연락처: ${user.phone} ")
                } else {
                    println("해당 유저가 존재하지 않습니다.")
                }
            }

            3 -> {
                println("조회 대상 이름을 입력해주세요")
                val list = findUserByName(readln())
                if(list.isNotEmpty()) {
                    list.forEach {
                        println("이름: ${it.name}, 이메일: ${it.email}, 연락처: ${it.phone} ")
                    }
                } else {
                    println("해당 유저가 존재하지 않습니다.")
                }
            }
            4 -> {
                val users = findAllUsers()

                if (users.isNotEmpty()) {
                    users.forEach {
                        println("이름: ${it.name}, 이메일: ${it.email}, 연락처: ${it.phone}")
                    }
                } else {
                    println("등록된 회원이 없습니다.")
                }
            }
            5 -> {
                println("수정 대상 이메일을 입력해주세요")
                updateUserByEmail(readln())
            }
            6 -> {
                println("삭제 대상 이메일을 입력해주세요")
                deleteUserByEmail(readln())
            }

            7 -> {
                println("프로그램을 종료합니다.")
                break
            }


            else -> println("잘못 입력하셨습니다. 1 ~ 7 사이의 숫자를 입력해주세요")

        }
    }
}

fun main(){
    val grade = selectGrade()
    choiceMenu(grade)
}
