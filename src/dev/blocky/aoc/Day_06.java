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

public class Day_06
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_06.txt");
        List<String> fileContent = Files.readAllLines(file.toPath(), UTF_8);

        // Part 1 of the Challenge.
        System.out.println(part1(fileContent));

        // Part 2 of the Challenge.
        System.out.println(part2(fileContent));
    }

    public static int part1(List<String> lines)
    {
        String time = lines.get(0).substring(5).strip();
        String[] timesRaw = time.split(" ");
        List<Integer> times = Arrays.stream(timesRaw).filter(tm -> !tm.isBlank()).mapToInt(Integer::parseInt).boxed().toList();

        String distance = lines.get(1).substring(9).strip();
        String[] distancesRaw = distance.split(" ");
        List<Integer> distances = Arrays.stream(distancesRaw).filter(dst -> !dst.isBlank()).mapToInt(Integer::parseInt).boxed().toList();

        List<Integer> possibleWins = new ArrayList<>();

        for (int i = 0; i < times.size(); i++)
        {
            int msTime = times.get(i);
            int mmDistance = distances.get(i);

            int possible = 0;

            for (int j = 1; j <= msTime; j++)
            {
                int newDistance = j * (msTime - j);

                if (mmDistance > newDistance)
                {
                    continue;
                }

                possible++;
            }

            possibleWins.add(possible);
        }
        return possibleWins.stream().reduce(1, Math::multiplyExact);
    }

    public static int part2(List<String> lines)
    {
        String time = lines.get(0).substring(5).replaceAll("\\s", "");
        String distance = lines.get(1).substring(9).replaceAll("\\s", "");

        long msTime = Long.parseLong(time);
        long mmDistance = Long.parseLong(distance);

        int possible = 0;

        for (long i = 1; i <= msTime; i++)
        {
            long newDistance = i * (msTime - i);

            if (mmDistance > newDistance)
            {
                continue;
            }

            possible++;
        }

        return possible;
    }
}
