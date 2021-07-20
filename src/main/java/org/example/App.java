package org.example;

import jodd.time.JulianDate;

import javax.xml.bind.DatatypeConverter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class App
{
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
    private static long factorJD = 210866760000009L;

    public static void main(String[] args)
    {
//        byte[] dateByte = {2, -14, 12, -85, -41, 35, 97, 22};
//        long dateLong = 212456156759195611L;
        long dateLong = 212246064628195611L;
        String date = dateLongToString(dateLong);
        System.out.println(date);
        byte[] search = dateStringToByte(date);
        System.out.println(dateLong);
        System.out.println(Arrays.toString(search));
    }

    public static String dateByteToString(byte[] date)
    {
        String hex = DatatypeConverter.printHexBinary(date);
        long dateLong = Long.parseUnsignedLong(hex, 16);
        return dateLongToString(dateLong);
    }

    public static String dateLongToString(Long date)
    {
        JulianDate jd = JulianDate.of(date/1000 - factorJD);
        return jd.toLocalDateTime().format(formatter);
    }

    public static byte[] dateStringToByte(String date)
    {
        int position = 0;
        int year = Integer.parseInt(date.substring(position, position += 4));
        int mount = Integer.parseInt(date.substring(position, position += 2));
        int day = Integer.parseInt(date.substring(position, position += 2));
        int hour = Integer.parseInt(date.substring(position, position += 2));
        int minute = Integer.parseInt(date.substring(position, position += 2));
        int second = Integer.parseInt(date.substring(position, position += 2));
        int mili = Integer.parseInt(date.substring(position, position + 2)) * 10;

        JulianDate js = JulianDate.of(year, mount, day, hour, minute, second, mili);
        long dateLong = (js.toMilliseconds() + factorJD) * 1000;
        System.out.println(dateLong);
        String hex = Long.toHexString(dateLong);
        if (hex.length() % 2 > 0)
        {
            hex = "0" + hex;
        }
        return DatatypeConverter.parseHexBinary(hex);
    }
}
