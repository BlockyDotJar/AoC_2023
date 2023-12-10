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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Day_09
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_09.txt");
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

            String[] historyValuesRaw = line.split(" ");
            List<Integer> historyValues = Arrays.stream(historyValuesRaw).mapToInt(Integer::parseInt).boxed().toList();
            List<Integer> differences = getDifferences(historyValues);

            int total = differences.get(differences.size() - 1) + historyValues.get(historyValues.size() - 1);
            int numbers = differences.stream().mapToInt(Integer::intValue).sum();

            while (numbers != 0)
            {
                differences = getDifferences(differences);
                total += differences.get(differences.size() - 1);
                numbers = differences.stream().mapToInt(Integer::intValue).sum();
            }
            sum += total;
        }
        return sum;
    }

    public static long part2(List<String> lines)
    {
        int sum = 0;

        for (String line : lines)
        {
            if (line.isBlank())
            {
                continue;
            }

            String[] historyValuesRaw = line.split(" ");
            List<Integer> historyValues = Arrays.stream(historyValuesRaw).mapToInt(Integer::parseInt).boxed().toList();
            List<Integer> differences = getDifferences(historyValues);

            int total = historyValues.get(0) - differences.get(0);
            int numbers = differences.stream().mapToInt(Integer::intValue).sum();

            int sign = 1;

            while (numbers != 0)
            {
                differences = getDifferences(differences);
                total += sign * differences.get(0);
                sign = -sign;
                numbers = differences.stream().mapToInt(Integer::intValue).sum();
            }
            sum += total;
        }
        return sum;
    }

    private static List<Integer> getDifferences(List<Integer> values)
    {
        ArrayList<Integer> differences = new ArrayList<>();

        int lastValue = values.get(0);

        for (int i = 0; i < values.size(); i++)
        {
            if (i == 0)
            {
                continue;
            }

            int value = values.get(i);

            differences.add(value - lastValue);

            lastValue = value;
        }
        return differences;
    }
}
