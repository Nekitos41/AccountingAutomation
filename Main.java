package sprint_1.accounting_automation;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static Scanner fileScanner;
    private static final ArrayList<MonthlyReport> listOfMonRep = new ArrayList<>();
    private static final ArrayList<YearlyReport> listOfYearRep = new ArrayList<>();

    public static void main(String[] args) {
        int num = 0;
        while (num != 6) {
            printMenu();
            System.out.print("Input your choice: ");
            num = inputNumber(1, 6);
            inputUserChoice(num);
        }
    }

    private static int inputNumber(int min, int max) {
        int num = 0;
        boolean isIncorrect;
        do {
            isIncorrect = false;
            try {
                num = Integer.parseInt(scan.nextLine());
            } catch (Exception q) {
                isIncorrect = true;
                System.out.println("Check data correctness.");
            }
            if (!isIncorrect && (num < min || num > max)) {
                isIncorrect = true;
                System.out.println("Invalid input range.");
            }
        } while (isIncorrect);
        return num;
    }

    private static String inputFilePath() {
        String path;
        boolean isIncorrect;
        do {
            isIncorrect = false;
            System.out.println("Input file path: ");
            path = scan.nextLine();
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("Wrong way to file. Input correct path.");
                isIncorrect = true;
            }
            if (!isIncorrect && !file.canRead()) {
                System.out.println("Impossible to read data from file.");
                isIncorrect = true;
            }
            if (!isIncorrect && !file.canWrite()) {
                System.out.println("Impossible to write data to file.");
                isIncorrect = true;
            }
        } while (isIncorrect);
        return path;
    }

    private static void inputUserChoice(int num) {
        String path;
        switch (num) {
            case 1:
                path = inputFilePath();
                inputMonthDataFromFile(path);
                break;
            case 2:
                path = inputFilePath();
                inputYearDataFromFile(path);
                break;
            case 3:
                compareReports();
                break;
            case 4:
                printMonthList();
                break;
            case 5:
                printYearList();
                break;
            case 6:
                System.exit(0);
                break;
        }
    }

    private static void printMenu() {
        final String str1 = "1 - Read monthly reports.\n";
        final String str2 = "2 - Read yearly reports.\n";
        final String str3 = "3 - Compare reports.\n";
        final String str4 = "4 - Output info about monthly reports.\n";
        final String str5 = "5 - Output info about yearly reports.\n";
        final String str6 = "6 - Close program.";
        System.out.println(str1 + str2 + str3 + str4 + str5 + str6);
    }

    private static void printMonthList() {
        System.out.println("Item_name Is_Expense Quantity Sum_One Month Year Total");
        String tab = "    ";
        for (MonthlyReport elem : listOfMonRep) {
            System.out.println(tab + elem.getItemName() + tab + elem.getExpense() + tab + elem.getQuantity() + tab
                    + elem.getSumOfOne() + tab + elem.getMonth() + tab + elem.getYear() + tab + elem.getSum());
        }
    }

    private static void printYearList() {
        System.out.println("Month Year Is_Expense Total");
        String tab = "    ";
        for (YearlyReport elem : listOfYearRep) {
            System.out.println(elem.getMonth() + tab + elem.getYear() + tab + elem.getExpense()
                    + tab + elem.getSum());
        }
    }

    private static void inputMonthDataFromFile(String path) {
        try {
            fileScanner = new Scanner(new File(path));
        } catch (Exception e) {
        }
        boolean isIncorrect = true;
        String str = "";
        while (fileScanner.hasNextLine() & isIncorrect) {
            try {
                str = fileScanner.nextLine();
            } catch (Exception e) {
                isIncorrect = false;
                System.out.println("Mistake of reading elements of matrix.");
            }
            if (isIncorrect) {
                String[] arr = str.split(", ");
                if (arr.length == 4) {
                    try {
                        MonthlyReport monthlyReport = new MonthlyReport();
                        int[] array = getDataAboutReport(path);
                        if (array.length > 0) {
                            boolean isExpense = checkDataCorrectness(arr[1]);
                            if (isExpense) {
                                monthlyReport.setExpense(Boolean.parseBoolean(arr[1]));
                            } else {
                                System.out.println("Check data correctness.");
                                listOfMonRep.clear();
                                break;
                            }
                            monthlyReport.setItemName(arr[0]);
                            monthlyReport.setQuantity(Integer.parseInt(arr[2]));
                            monthlyReport.setSumOfOne(Integer.parseInt(arr[3]));
                            monthlyReport.setMonth(array[1]);
                            monthlyReport.setYear(array[0]);
                            int quantity = monthlyReport.getQuantity();
                            int sumOfOne = monthlyReport.getSumOfOne();
                            if (monthlyReport.getExpense())
                                monthlyReport.setSum(-quantity * sumOfOne);
                            else
                                monthlyReport.setSum(quantity * sumOfOne);
                            listOfMonRep.add(monthlyReport);
                        } else {
                            isIncorrect = false;
                        }
                    } catch (Exception e) {
                        isIncorrect = false;
                        System.out.println("Check data correctness.");
                    }
                } else {
                    isIncorrect = false;
                    System.out.println("Check data correctness.");
                }
            }
        }
        if (isIncorrect)
            System.out.println("Successful reading of information from file.");
    }

    private static void inputYearDataFromFile(String path) {
        try {
            fileScanner = new Scanner(new File(path));
        } catch (Exception e) {
        }
        boolean isIncorrect = true;
        String str = "";
        while (fileScanner.hasNextLine() & isIncorrect) {
            try {
                str = fileScanner.nextLine();
            } catch (Exception e) {
                isIncorrect = false;
                System.out.println("Mistake of reading elements of matrix.");
            }
            if (isIncorrect) {
                String[] arr = str.split(", ");
                if (arr.length == 3) {
                    try {
                        YearlyReport yearlyReport = new YearlyReport();
                        int[] array = getDataAboutReport(path);
                        if (array.length > 0) {
                            boolean isCorrect = checkMonth(Integer.parseInt(arr[0]));
                            if (isCorrect) {
                                yearlyReport.setExpense(isCorrect);
                            } else {
                                System.out.println("Check data correctness.");
                                listOfYearRep.clear();
                                break;
                            }
                            yearlyReport.setMonth(Integer.parseInt(arr[0]));
                            yearlyReport.setYear(array[0]);
                            boolean isExpense = checkDataCorrectness(arr[2]);
                            if (isExpense) {
                                yearlyReport.setExpense(Boolean.parseBoolean(arr[2]));
                            } else {
                                System.out.println("Check data correctness.");
                                listOfYearRep.clear();
                                break;
                            }
                            if (yearlyReport.getExpense())
                                yearlyReport.setSum(-Integer.parseInt(arr[1]));
                            else
                                yearlyReport.setSum(Integer.parseInt(arr[1]));
                            listOfYearRep.add(yearlyReport);
                        } else {
                            isIncorrect = false;
                        }
                    } catch (Exception e) {
                        isIncorrect = false;
                        System.out.println("Check data correctness.");
                    }
                } else {
                    isIncorrect = false;
                    System.out.println("Check data correctness.");
                }
            }
        }
        if (isIncorrect)
            System.out.println("Successful reading of information from file.");
    }

    private static int[] getDataAboutReport(String path) {
        final int MONTH_LENGTH = 12;
        final int YEAR_LENGTH = 10;
        int[] arr = new int[0];
        int year = 0, month = 0;
        boolean isIncorrect = true;
        String path2 = path;
        path = path.substring(path.length() - MONTH_LENGTH);
        path2 = path.substring(path.length() - YEAR_LENGTH);
        if (path.charAt(0) == 'm') {
            try {
                year = Integer.parseInt(path.substring(2, 6));
            } catch (Exception e) {
                isIncorrect = false;
            }
            try {
                month = Integer.parseInt(path.substring(6, 8));
            } catch (Exception e) {
                isIncorrect = false;
            }
            if (isIncorrect) {
                arr = new int[2];
                arr[0] = year;
                arr[1] = month;
            }
        } else if (path2.charAt(0) == 'y' && isIncorrect) {
            try {
                year = Integer.parseInt(path2.substring(2, 6));
            } catch (Exception e) {
                isIncorrect = false;
            }
            if (isIncorrect) {
                arr = new int[1];
                arr[0] = year;
            }
        } else {
            System.out.println("Incorrect file path.");
        }
        return arr;
    }

    private static int[] setTotalItems() {
        int sum = 0;
        int sumO = 0;
        for (MonthlyReport elem : listOfMonRep) {
            if (elem.getSum() > 0) {
                sum += elem.getSum();
                elem.setTotalProfit(sum);
            } else {
                sumO += elem.getSum();
                elem.setTotalExpense(sumO);
            }
        }
        return new int[]{sum, sumO};
    }

    private static void compareReports() {
        int[] arr = setTotalItems();
        int sum = 0;
        int sumO = 0;
        int month = listOfMonRep.get(0).getMonth();
        for (YearlyReport elem : listOfYearRep) {
            if (elem.getMonth() == month) {
                if (elem.getSum() > 0) {
                    sum += elem.getSum();
                } else {
                    sumO += elem.getSum();
                }
            }
        }
        if (sum == arr[0] && sumO == arr[1]) {
            System.out.println("Reports converge.");
        } else {
            System.out.println("Reports not converge.");
        }
    }

    private static boolean checkDataCorrectness(String str) {
        boolean isIncorrect = true;
        if (!(str.equals("true") || str.equals("false"))) {
            isIncorrect = false;
        }
        return isIncorrect;
    }

    private static boolean checkMonth(int num) {
        final int MIN_NUM = 1;
        final int MAX_NUM = 12;
        boolean isIncorrect = true;
        if ((num < MIN_NUM) || (num > MAX_NUM)) {
            isIncorrect = false;
        }
        return isIncorrect;
    }
}
