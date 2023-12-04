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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Day_03
{
    private static final ArrayList<Number> numbers = new ArrayList<>();
    private static final ArrayList<Symbol> symbols = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_03.txt");
        List<String> fileContent = Files.readAllLines(file.toPath(), UTF_8);

        // Part 1 of the Challenge.
        System.out.println(part1(fileContent));

        // Part 2 of the Challenge.
        System.out.println(part2());
    }

    public static int part1(List<String> lines)
    {
        Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

        int y = 0;

        for (String line : lines)
        {
            if (line.isBlank())
            {
                continue;
            }

            Matcher NUMBER_MATCHER = NUMBER_PATTERN.matcher(line);

            while (NUMBER_MATCHER.find())
            {
                String numbStr = line.substring(NUMBER_MATCHER.start(), NUMBER_MATCHER.end());
                int numb = Integer.parseInt(numbStr);

                Number number = new Number(numb, NUMBER_MATCHER.start(), NUMBER_MATCHER.end() - 1, y);
                numbers.add(number);
            }

            char[] chars = line.toCharArray();

            int x = 0;

            for (char ch : chars)
            {
                if (ch != '.' && !Character.isDigit(ch))
                {
                    Symbol symbol = new Symbol(ch, x, y);
                    symbols.add(symbol);
                }
                x++;
            }
            y++;
        }
        return numbers.stream().filter(numb -> symbols.stream().anyMatch(numb::hasAdjacent)).mapToInt(Number::number).sum();
    }

    public static int part2()
    {
        return symbols.stream().filter(symbol -> numbers.stream().filter(number -> number.hasAdjacent(symbol)).count() == 2)
                .mapToInt(symbol -> numbers.stream().filter(number -> number.hasAdjacent(symbol)).mapToInt(Number::number).reduce((left, right) -> left * right).orElse(0))
                .sum();
    }

    record Number(int number, int start, int end, int y)
    {
        public boolean hasAdjacent(Symbol symbol)
        {
            return Math.abs(this.y() - symbol.y()) <= 1 && symbol.x() <= this.end + 1 && symbol.x() >= this.start - 1;
        }
    }

    record Symbol(char symbol, int x, int y)
    {
    }
}
