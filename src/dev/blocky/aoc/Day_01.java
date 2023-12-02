/**
 * VorteX - General utility program written in Java.
 * Copyright (C) 2023 BlockyDotJar (aka. Dominic R.)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package dev.blocky.aoc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Day_01
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_01.txt");
        List<String> fileContent = Files.readAllLines(file.toPath(), UTF_8);

        // Part 1 of the Challenge.
        System.out.println(part1(fileContent));

        // Part 2 of the Challenge.
        System.out.println(part2(fileContent));
    }

    public static int part1(List<String> lines)
    {
        int sum = 0;

        for (String line : lines)
        {
            int[] numbers = line.chars().filter(Character::isDigit).map(Character::getNumericValue).toArray();
            sum += Integer.parseInt(Integer.toString(numbers[0]) + numbers[numbers.length - 1]);
        }
        return sum;
    }

    public static int part2(List<String> lines)
    {
        List<String> numbers = List.of
                (
                        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
                );

        int sum = 0;

        for (String line : lines)
        {
            for (int i = 0; i < numbers.size(); i++)
            {
                String number = numbers.get(i);
                line = line.replaceAll(number, number + (i + 1) + number);
            }

            int[] realNumbers = line.chars().filter(Character::isDigit).map(Character::getNumericValue).toArray();

            String firstNumber = Integer.toString(realNumbers[0]);
            String lastNumber = Integer.toString(realNumbers[realNumbers.length - 1]);
            String twoDigitNumber = firstNumber + lastNumber;

            sum += Integer.parseInt(twoDigitNumber);
        }
        return sum;
    }
}
