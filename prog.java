import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class prog {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Введите данные в формате: Фамилия Имя Отчество датарождения номертелефона пол");
            String input = scanner.nextLine();
            
            String[] data = input.split(" ");
            if (data.length != 6) {
                throw new IllegalArgumentException("Неверное количество данных. Ожидалось 6 параметров.");
            }
            
            String lastName = data[0];
            String firstName = data[1];
            String middleName = data[2];
            String birthDate = data[3];
            String phoneNumber = data[4];
            String gender = data[5];
            
            validateData(birthDate, phoneNumber, gender);
            
            String fileName = lastName + ".txt";
            FileWriter writer = new FileWriter(fileName, true);
            String userData = lastName + firstName + middleName + birthDate + " " + phoneNumber + gender;
            writer.write(userData + System.lineSeparator());
            writer.close();
            
            System.out.println("Данные успешно записаны в файл " + fileName);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void validateData(String birthDate, String phoneNumber, String gender) {
        // Проверка формата даты (dd.mm.yyyy)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false); // Строгая проверка формата

        try {
            dateFormat.parse(birthDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Неверный формат даты рождения. Ожидается dd.mm.yyyy");
        }

        // Проверка формата номера телефона (без форматирования)
        Pattern phonePattern = Pattern.compile("\\d{10}"); // Допустим 10 цифр
        Matcher phoneMatcher = phonePattern.matcher(phoneNumber);
        if (!phoneMatcher.matches()) {
            throw new IllegalArgumentException("Неверный формат номера телефона. Ожидается 10 цифр.");
        }

        // Проверка формата пола (f или m)
        if (!"f".equalsIgnoreCase(gender) && !"m".equalsIgnoreCase(gender)) {
            throw new IllegalArgumentException("Неверный формат пола. Ожидается 'f' или 'm'.");
        }
    }
}
