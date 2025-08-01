package org.example;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    //단계별 실행용 enum
    public enum levelSet {
        LEVEL_1(1),
        LEVEL_2(2),
        LEVEL_3(3),
        LEVEL_4(4),
        LEVEL_5(5),
        LEVEL_6(6),
        LEVEL_7(7),
        LEVEL_8(8);

        private final int value;

        levelSet(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // 명령 구분용 enum
    public enum commendSet {
        NewREGISTER(1),
        UPDATE(2),
        DELETE(3),
        EXIT(4),
        REGISTER(5),
        VIEW(9);

        private final int value;

        commendSet(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum modSet {
        MAIN(0),
        UPDATE(2),
        DELETE(3),
        EXIT(4),
        REGISTER(5),
        VIEW(9);

        private final int value;

        modSet(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // 전역 변수
    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<String> writerList = new ArrayList<>(); //작가 목록
    public static ArrayList<String> quoteList = new ArrayList<>();
    public static ArrayList<String> flagList = new ArrayList<>();//명언 목록


    // 입력 처리 및 검증
    public static String getUserInput() {
        String input = sc.nextLine();
        return validateAndCleanInput(input);
    }

    // 특문 제거
    public static String removeSpecialCharacters(String input) {
        String regex = "[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s]";
        return input.replaceAll(regex, "");
    }

    //특수 문자 체크
    public static String validateAndCleanInput(String input) {
        String regex = "[!@#$%^&*]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String result = input;

        if (matcher.find()) {
            System.out.println("특수문자는 입력할 수 없습니다.");
            result = removeSpecialCharacters(result);
        }
        return result;
    }


    // 명령어 분석
    public static int analyzeCommand(String command) {
        if (command.equals("등록")) {
            return commendSet.REGISTER.getValue();
        } else if (command.equals("수정")) {
            return commendSet.UPDATE.getValue();
        } else if (command.equals("삭제")) {
            return commendSet.DELETE.getValue();
        } else if (command.equals("조회")) {
            return commendSet.VIEW.getValue();
        } else {
            return commendSet.EXIT.getValue();
        }
    }

    // 레벨 전환 처리
    public static void switchToLevel(int level,int mod) {
        switch (level) {
            case 1:
                runLevel1(mod);
                break;
            case 2:
                runLevel2(mod);
                break;
            case 3:
                runLevel3(mod);
                break;
            case 4:
                runLevel4(mod);
                break;
            case 5:
                runLevel5(mod);
                break;
            case 6:
                runLevel6(mod);
                break;
            case 8:
                runLevel8(mod);
                break;
        }
    }

    // 공통 메뉴 출력
    public static void showAppHeader() {
        System.out.println("== 명언 앱 ==");
    }

    public static void showMenuCommands(String commands) {
        System.out.println("명령어: " + commands);
    }

    public static void showUserCommand(String command) {
        System.out.println("명령) " + command);
    }

    // 공통 명언 등록 처리
    public static void processQuoteRegistration(int level) {
        System.out.println("명언을 입력해주세요.");
        String quote = getUserInput();
        System.out.println("명언 :" + quote);

        System.out.println("작가를 입력해주세요.");
        String author = getUserInput();
        System.out.println("작가 :" + author);

        if(level >= levelSet.LEVEL_4.getValue()){
            writerList.add(author);
            quoteList.add(quote);
            flagList.add("Y");
        }
        //return poem;
    }
    //조회
    public static void viewQuote(){
        for (int i=0; i < writerList.size(); i++){
            if(flagList.get(i).equals("Y"))
                System.out.println(i+1 +" / " + writerList.get(i) + " / " + quoteList.get(i));
            else
                System.out.println("삭제된 명언 입니다.");
        }
        showUserCommand("종료");
    }

    //삭제
    public static void deleteQuote(int level){
        System.out.println("삭제하실 명언 번호를 입력해주세요.");
        int id = Integer.parseInt(getUserInput())-1;
        if(flagList.get(id).equals("Y"))
            flagList.set(id,"N");
        else
            System.out.println(id+"번 명언은 존재하지 않습니다.");
        if(flagList.get(id).equals("N"))
            System.out.println("성공적으로 삭제 되었습니다.");
        switchToLevel(level,modSet.VIEW.getValue());
    }
    //수정
    public static void updateQuote(int level){
        System.out.println("수정하실 명언 번호를 입력해주세요.");
        int id = Integer.parseInt(getUserInput())-1;
        System.out.println("수정하실 명언 내용을 입력해주세요.");
        String quote = getUserInput();
        quoteList.set(id,quote);
        switchToLevel(level,modSet.VIEW.getValue());
    }

    public static void handleNextCommand(int level) {
        System.out.println("다음 명령을 내려주세요.");
        String nextCommand = getUserInput();
        int commandValue = analyzeCommand(nextCommand);
        showUserCommand(nextCommand);
        if (commandValue == commendSet.REGISTER.getValue()) {
            switchToLevel(level,modSet.REGISTER.getValue());
        } else if (commandValue == commendSet.EXIT.getValue()) {

        } else if (commandValue == commendSet.DELETE.getValue()) {
            switchToLevel(level,modSet.DELETE.getValue());
        } else if (commandValue == commendSet.VIEW.getValue()) {
            switchToLevel(level,modSet.VIEW.getValue());
        } else if (commandValue == commendSet.UPDATE.getValue()) {
            switchToLevel(level,modSet.UPDATE.getValue());
        } else {
            System.out.println("잘못된 접근입니다.");
        }
    }

    // Level 1 (기본 단계)
    public static void runLevel1(int mod) {
        showAppHeader();
        System.out.println("명령) ");
    }

    // Level 2 (등록 기능 추가)
    public static void runLevel2(int mod) {
        String command;
        int commandType;

        if (mod == modSet.MAIN.getValue()) {
            showAppHeader();
            showMenuCommands("등록,종료");
            command = getUserInput();
            showUserCommand(command);
            commandType = analyzeCommand(command);
        } else if (mod == modSet.REGISTER.getValue()) {
            commandType = commendSet.REGISTER.getValue();
        } else {
            commandType = commendSet.EXIT.getValue();
        }

        switch (commandType) {
            case 1:
                processQuoteRegistration(levelSet.LEVEL_2.getValue());
                showUserCommand("종료");
                break;
            case 4:
                break;
        }
    }

    // Level 3 (등록된 명언 count 기능 추가)
    public static void runLevel3(int mod) {
        String command;
        int commandType;
        int counter = 0;

        if (mod == modSet.MAIN.getValue()) {
            showAppHeader();
            showMenuCommands("등록 종료");
            command = getUserInput();
            showUserCommand(command);
            commandType = analyzeCommand(command);
        } else {
            commandType = commendSet.EXIT.getValue();
        }

        switch (commandType) {
            case 1:
                processQuoteRegistration(levelSet.LEVEL_3.getValue());
                counter++;
                System.out.println(counter + "번 명언이 등록되었습니다.");
                showUserCommand("종료");
                break;
            case 4:
                break;
        }
    }
    //추가 등록 기능(list) 추가
    public static void runLevel4(int mod) {
        String command;
        int commandType;

        if (mod == modSet.MAIN.getValue()) {
            showAppHeader();
            showMenuCommands("등록 종료");
            command = getUserInput();
            showUserCommand(command);
            commandType = analyzeCommand(command);
            if(commandType != commendSet.EXIT.getValue())
                commandType = commendSet.NewREGISTER.getValue();
        } else if (mod == modSet.REGISTER.getValue()) {
            commandType = commendSet.REGISTER.getValue();
        } else {
            commandType = commendSet.EXIT.getValue();
        }

        switch (commandType) {
            case 1:
                processQuoteRegistration(levelSet.LEVEL_4.getValue());
                System.out.println(writerList.size() + "번 명언이 등록되었습니다.");
                handleNextCommand(levelSet.LEVEL_4.getValue());
                break;
            case 4:
                break;
            case 5:
                processQuoteRegistration(levelSet.LEVEL_4.getValue());
                System.out.println(writerList.size() + "번 명언이 등록되었습니다.");
                handleNextCommand(levelSet.LEVEL_4.getValue());
        }
    }

    //추가 등록된 명언 조회 기능 추가
    public static void runLevel5(int mod) {
        String command;
        int commandType;

        if (mod == modSet.MAIN.getValue()) {
            showAppHeader();
            showMenuCommands("등록 종료 조회");
            command = getUserInput();
            showUserCommand(command);
            commandType = analyzeCommand(command);
            if(commandType != commendSet.EXIT.getValue())
                commandType = commendSet.NewREGISTER.getValue();
        } else if (mod == modSet.REGISTER.getValue()) {
            commandType = commendSet.REGISTER.getValue();
        } else if(mod == modSet.VIEW.getValue()){
            commandType = commendSet.VIEW.getValue();
        } else {
            commandType = commendSet.EXIT.getValue();
        }

        switch (commandType) {
            case 1:
                processQuoteRegistration(levelSet.LEVEL_5.getValue());
                System.out.println(writerList.size() + "번 명언이 등록되었습니다.");
                handleNextCommand(levelSet.LEVEL_5.getValue());
                break;
            case 4:
                break;
            case 5:
                processQuoteRegistration(levelSet.LEVEL_5.getValue());
                System.out.println(writerList.size() + "번 명언이 등록되었습니다.");
                handleNextCommand(levelSet.LEVEL_5.getValue());
                break;
            case 9:
                viewQuote();
                break;
        }
    }

    //추가 등록된 명언 삭제 기능 추가,lv7 예외처리도 동시에 구현
    public static void runLevel6(int mod) {
        String command;
        int commandType;

        if (mod == modSet.MAIN.getValue()) {
            showAppHeader();
            showMenuCommands("등록 종료 조회 삭제");
            command = getUserInput();
            showUserCommand(command);
            commandType = analyzeCommand(command);
            if(commandType != commendSet.EXIT.getValue())
                commandType = commendSet.NewREGISTER.getValue();
        } else if (mod == modSet.REGISTER.getValue()) {
            commandType = commendSet.REGISTER.getValue();
        } else if(mod == modSet.VIEW.getValue()){
            commandType = commendSet.VIEW.getValue();
        } else if(mod == modSet.DELETE.getValue()){
            commandType = commendSet.DELETE.getValue();
        } else {
            commandType = commendSet.EXIT.getValue();
        }

        switch (commandType) {
            case 1:
                processQuoteRegistration(levelSet.LEVEL_6.getValue());
                System.out.println(writerList.size() + "번 명언이 등록되었습니다.");
                handleNextCommand(levelSet.LEVEL_6.getValue());
                break;
            case 3:
                deleteQuote(levelSet.LEVEL_6.getValue());
                break;
            case 4:
                break;
            case 5:
                processQuoteRegistration(levelSet.LEVEL_6.getValue());
                System.out.println(writerList.size() + "번 명언이 등록되었습니다.");
                handleNextCommand(levelSet.LEVEL_6.getValue());
                break;
            case 6:

                break;
            case 9:
                viewQuote();
                break;
        }
    }

    //추가 등록된 명언 수정 기능 추가
    public static void runLevel8(int mod) {
        String command;
        int commandType;

        if (mod == modSet.MAIN.getValue()) {
            showAppHeader();
            showMenuCommands("등록 종료 조회 삭제");
            command = getUserInput();
            showUserCommand(command);
            commandType = analyzeCommand(command);
            if(commandType != commendSet.EXIT.getValue())
                commandType = commendSet.NewREGISTER.getValue();
        } else if (mod == modSet.REGISTER.getValue()) {
            commandType = commendSet.REGISTER.getValue();
        } else if(mod == modSet.VIEW.getValue()){
            commandType = commendSet.VIEW.getValue();
        } else if(mod == modSet.UPDATE.getValue()){
            commandType = commendSet.UPDATE.getValue();
        } else if(mod == modSet.DELETE.getValue()){
            commandType = commendSet.DELETE.getValue();
        } else {
            commandType = commendSet.EXIT.getValue();
        }
        switch (commandType) {
            case 1:
                processQuoteRegistration(levelSet.LEVEL_8.getValue());
                System.out.println(writerList.size() + "번 명언이 등록되었습니다.");
                handleNextCommand(levelSet.LEVEL_8.getValue());
                break;
            case 2:
                updateQuote(levelSet.LEVEL_8.getValue());
                break;
            case 3:
                deleteQuote(levelSet.LEVEL_8.getValue());
                break;
            case 4:
                break;
            case 5:
                processQuoteRegistration(levelSet.LEVEL_8.getValue());
                System.out.println(writerList.size() + "번 명언이 등록되었습니다.");
                handleNextCommand(levelSet.LEVEL_8.getValue());
                break;
            case 6:

                break;
            case 9:
                viewQuote();
                break;
        }
    }

    public static void closeScanner() {
        sc.close();
    }

    public static void main(String[] args) {
        // runLevel1(modSet.MAIN.getValue());
        //runLevel2(modSet.MAIN.getValue());
        //runLevel3(modSet.MAIN.getValue());
        //runLevel4(modSet.MAIN.getValue());
        //runLevel5(modSet.MAIN.getValue());
        //runLevel6(modSet.MAIN.getValue());
        runLevel8(modSet.MAIN.getValue());
        closeScanner();
    }
}