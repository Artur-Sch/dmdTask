

import unisoft.ws.FNSNDSCAWS2;
import unisoft.ws.FNSNDSCAWS2Port;
import unisoft.ws.fnsndscaws2.request.NdsRequest2;
import unisoft.ws.fnsndscaws2.request.ObjectFactory;
import unisoft.ws.fnsndscaws2.response.NdsResponse2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


/**
 * Задание для Домодедово
 *
 * Шнайдер А.Е (artur@schneider-dev.ru)
 */


public class Main {
    private static String INN;
    private static String DATE;


    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DATE = formatter.format(LocalDate.now());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ИНН");
        INN = scanner.nextLine();


        FNSNDSCAWS2 fns = new FNSNDSCAWS2();
        FNSNDSCAWS2Port fnsPort = fns.getFNSNDSCAWS2Port();
        NdsRequest2.NP requestNP = new ObjectFactory().createNdsRequest2NP();
        requestNP.setINN(INN);
        requestNP.setDT(DATE);

        NdsRequest2 request = new ObjectFactory().createNdsRequest2();
        request.getNP().add(requestNP);
        NdsResponse2 response = fnsPort.ndsRequest2(request);

        System.out.printf("%s", STATES[Integer.valueOf(response.getNP().get(0).getState())]);

    }


    private static final String[] STATES = {
            "0 -Налогоплательщик зарегистрирован в ЕГРН и имел статус действующего в указанную дату",
            "1 - Налогоплательщик зарегистрирован в ЕГРН, но не имел статус действующего в указанную дату",
            "2 - Налогоплательщик зарегистрирован в ЕГРН",
            "3 - Налогоплательщик с указанным ИНН зарегистрирован в ЕГРН, КПП не соответствует ИНН или не указан",
            "4 - Налогоплательщик с указанным ИНН не зарегистрирован в ЕГРН",
            "5 - Некорректный ИНН",
            "6 - Недопустимое количество символов ИНН",
            "7 - Недопустимое количество символов КПП",
            "8 - Недопустимые символы в ИНН",
            "9 - Недопустимые символы в КПП",
            "11 - некорректный формат даты",
            "12 - некорректная дата (ранее 01.01.1991 или позднее текущей даты)",
    };


}

