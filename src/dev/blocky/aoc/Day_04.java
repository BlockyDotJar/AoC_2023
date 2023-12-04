/**
 * Solutions for Advent of Code 2023.
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
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Day_04
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_04.txt");
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
            if (line.isBlank())
            {
                continue;
            }

            String[] cardParts = line.split("\\|");

            String[] winningNumbersRaw = cardParts[0]
                    .substring(9)
                    .strip()
                    .split(" ");

            String[] myNumbersRaw = cardParts[1]
                    .strip()
                    .split(" ");

            List<Integer> winningNumbers = Arrays.stream(winningNumbersRaw)
                    .filter(str -> !str.isBlank())
                    .mapToInt(str -> Integer.parseInt(str.strip()))
                    .boxed()
                    .toList();

            List<Integer> myNumbers = Arrays.stream(myNumbersRaw)
                    .filter(str -> !str.isBlank())
                    .mapToInt(str -> Integer.parseInt(str.strip()))
                    .boxed()
                    .toList();

            List<Integer> matchingNumbers = myNumbers.stream()
                    .filter(winningNumbers::contains)
                    .mapToInt(Integer::intValue)
                    .boxed()
                    .toList();

            int points = 0;

            for (int i = 0; i < matchingNumbers.size(); i++)
            {
                if (i == 0)
                {
                    points++;
                    continue;
                }

                points = (points * 2);
            }
            sum += points;
        }
        return sum;
    }

    public static int part2(List<String> lines)
    {
        int copies = 0;
        int card = 0;

        int[] cards = new int[lines.size()];
        Arrays.fill(cards, 1);

        for (String line : lines)
        {
            if (line.isBlank())
            {
                continue;
            }

            String[] cardParts = line.split("\\|");

            String[] winningNumbersRaw = cardParts[0]
                    .substring(9)
                    .strip()
                    .split(" ");

            String[] myNumbersRaw = cardParts[1]
                    .strip()
                    .split(" ");

            List<Integer> winningNumbers = Arrays.stream(winningNumbersRaw)
                    .filter(str -> !str.isBlank())
                    .mapToInt(str -> Integer.parseInt(str.strip()))
                    .boxed()
                    .toList();

            List<Integer> myNumbers = Arrays.stream(myNumbersRaw)
                    .filter(str -> !str.isBlank())
                    .mapToInt(str -> Integer.parseInt(str.strip()))
                    .boxed()
                    .toList();

            long matchesRaw = myNumbers.stream()
                    .filter(winningNumbers::contains)
                    .mapToInt(Integer::intValue)
                    .count();

            int matches = Math.toIntExact(matchesRaw);

            int cardValue = cards[card];
            copies += cardValue;

            for (int i = 1; i <= matches; i++)
            {
                if (card + i < lines.size())
                {
                    cards[card + i] += cardValue;
                }
            }
            card++;
        }
        return copies;
    }
}
