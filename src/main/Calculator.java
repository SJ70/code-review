package src.main;

import java.io.*;
import java.util.*;

public class Calculator {

    ArrayList<String> history = new ArrayList<>();
    BufferedReader bufferedReader;
    MenuItem[] menuItems;

    public Calculator(BufferedReader bufferedReader){
        this.bufferedReader = bufferedReader;
        initMenuItems();
    }
    private void initMenuItems(){
        menuItems = new MenuItem[] {
                new MenuItem("조회", () -> this.displayHistory()),
                new MenuItem("계산", () -> {
                    String expression = this.input();
                    long result = calculate(expression);
                    System.out.println("\n" + result + "\n");
                    addHistory(expression, result);
                })
        };
    }

    public void run() {
        showMenu();
        menuItems[selectMenuItemIndex()].operation.run();
    }
    private void showMenu(){
        StringBuilder stringBuilder = new StringBuilder();
        int menuItemCnt = menuItems.length;
        for(int i=0; i<menuItemCnt; i++){
            stringBuilder.append(i+1).append(". ").append(menuItems[i].title).append('\n');
        }
        stringBuilder.append("\n선택 : ");
        System.out.print(stringBuilder);
    }
    private int selectMenuItemIndex(){
        int selectedMenu = Integer.parseInt(input());
        System.out.println();
        return selectedMenu - 1;
    }

    private void addHistory(String expression, long result){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(expression).append(" = ").append(result);
        this.history.add(stringBuilder.toString());
    }
    private void displayHistory(){
        if(this.history.size()==0) return;

        StringBuilder stringBuilder = new StringBuilder();
        this.history.forEach((string -> stringBuilder.append(string).append('\n')));
        System.out.println(stringBuilder);
    }

    private long calculate(String expression){
        StringTokenizer stringTokenizer = new StringTokenizer(expression);

        long resultValue = 0;

        long currentValue = Long.parseLong(stringTokenizer.nextToken());
        String currentSymbol = "+";

        while(stringTokenizer.hasMoreTokens()){
            String nextSymbol = stringTokenizer.nextToken();
            long nextValue = Long.parseLong(stringTokenizer.nextToken());

//            System.out.printf("%s%d  |  %d %s %d %s %d\n",nextSymbol,nextValue,resultValue,currentSymbol,currentValue,nextSymbol,nextValue);

            switch(nextSymbol){
                case "+" :
                    switch(currentSymbol){
                        case "+" : resultValue += currentValue; break;
                        case "-" : resultValue -= currentValue; break;
                    }
                    currentValue = nextValue;
                    currentSymbol = "+";
                    break;
                case "-" :
                    switch(currentSymbol){
                        case "+" : resultValue += currentValue; break;
                        case "-" : resultValue -= currentValue; break;
                    }
                    currentValue = nextValue;
                    currentSymbol = "-";
                    break;
                case "*" :
                    currentValue *= nextValue;
                    break;
                case "/" :
                    currentValue /= nextValue;
                    break;
            }
        }

        switch(currentSymbol){
            case "+" : resultValue += currentValue; break;
            case "-" : resultValue -= currentValue; break;
        }

        return resultValue;
    }

    private String input() {
        try{
            return this.bufferedReader.readLine();
        }
        catch(IOException e){
            return "";
        }
    }
}
