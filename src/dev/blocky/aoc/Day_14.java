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

import dev.blocky.aoc.annotations.Unsolved;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Day_14
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_14.txt");
        List<String> fileContent = Files.readAllLines(file.toPath(), UTF_8);

        // Part 1 of the Challenge.
        System.out.println(part1(fileContent));

        // Part 2 of the Challenge.
        System.out.println(part2(fileContent));
    }

    public static int part1(List<String> lines)
    {
        ArrayList<Character> rocks = new ArrayList<>();

        for (String line : lines)
        {
            if (line.isBlank())
            {
                continue;
            }

            List<Character> chars = line.chars().mapToObj(ch -> (char) ch).toList();
            rocks.addAll(chars);
        }

        ArrayList<String> endPositions = new ArrayList<>();

        int length = lines.size();

        for (int i = 0; i < length; i++)
        {
            ArrayList<Character> verticalRocks = new ArrayList<>();

            char firstRock = rocks.get(i);
            verticalRocks.add(firstRock);

            for (int j = (length + i); j < rocks.size(); j++)
            {
                char nextRock = rocks.get(j);
                verticalRocks.add(nextRock);

                j += 99;
            }

            int lastCubeShapedRock = -1;

            for (int j = 0; j < verticalRocks.size(); j++)
            {
                char verticalRock = verticalRocks.get(j);

                if (verticalRock == '#')
                {
                    lastCubeShapedRock = j;
                    continue;
                }

                if (verticalRock == 'O' && lastCubeShapedRock < j)
                {
                    char rock = verticalRocks.remove(j);
                    verticalRocks.add(lastCubeShapedRock + 1, rock);
                }

                char rock = verticalRocks.remove(j);
                verticalRocks.add(j, rock);
            }

            String finalPosition = verticalRocks.stream().map(String::valueOf).collect(Collectors.joining());
            endPositions.add(finalPosition);
        }

        int sum = 0;

        for (String endPosition : endPositions)
        {
            char[] endPositionChars = endPosition.toCharArray();
            int lineCount = lines.size();

            for (char endPositionChar : endPositionChars)
            {
                if (endPositionChar == 'O')
                {
                    sum += lineCount;
                }
                lineCount--;
            }
        }
        return sum;
    }

    @Unsolved(willSolveInFuture = false)
    public static long part2(@SuppressWarnings("unused") List<String> lines)
    {
        return -1;
    }
}
