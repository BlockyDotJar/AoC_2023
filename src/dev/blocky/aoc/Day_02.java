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
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Day_02
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_02.txt");
        List<String> fileContent = Files.readAllLines(file.toPath(), UTF_8);

        // Part 1 of the Challenge.
        System.out.println(part1(fileContent));

        // Part 2 of the Challenge.
        System.out.println(part2(fileContent));
    }

    public static int part1(List<String> lines)
    {
        int gamesPossible = 0;

        for (String line : lines)
        {
            if (line.isBlank())
            {
                continue;
            }

            String[] gameParts = line.split(":");
            String[] subsets = gameParts[1].split(";");

            String game = gameParts[0].substring(5);

            boolean gamePossible = true;

            for (String subset : subsets)
            {
                subset = subset.strip();

                String[] parts = subset.split(",");

                int red = 0, green = 0, blue = 0;

                for (String part : parts)
                {
                    part = part.strip();

                    String[] item = part.split(" ");

                    int count = Integer.parseInt(item[0]);
                    String type = item[1];

                    switch (type)
                    {
                        case "red" -> red += count;
                        case "green" -> green += count;
                        case "blue" -> blue += count;
                    }
                }

                if (red > 12 || green > 13 || blue > 14)
                {
                    gamePossible = false;
                    break;
                }
            }

            if (gamePossible)
            {
                gamesPossible += Integer.parseInt(game);
            }
        }
        return gamesPossible;
    }

    public static int part2(List<String> lines)
    {
        int power = 0;

        for (String line : lines)
        {
            if (line.isBlank())
            {
                continue;
            }

            String[] gameParts = line.split(":");
            String[] parts = gameParts[1].replaceAll(";", ",").split(",");

            int red = -1, green = -1, blue = -1;

            for (String part : parts)
            {
                part = part.strip();

                String[] item = part.split(" ");

                int count = Integer.parseInt(item[0]);
                String type = item[1];

                switch (type)
                {
                    case "red" ->
                    {
                        if (red == -1 || count > red)
                        {
                            red = count;
                        }
                    }
                    case "green" ->
                    {
                        if (green == -1 || count > green)
                        {
                            green = count;
                        }
                    }
                    case "blue" ->
                    {
                        if (blue == -1 || count > blue)
                        {
                            blue = count;
                        }
                    }
                }
            }
            power += (red * green * blue);
        }
        return power;
    }
}
