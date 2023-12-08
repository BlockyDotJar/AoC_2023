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
import java.util.HashMap;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Day_08
{
    private static final HashMap<String, String> documents = new HashMap<>();
    private static char[] instructions = { };

    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_08.txt");
        List<String> fileContent = Files.readAllLines(file.toPath(), UTF_8);

        // Part 1 of the Challenge.
        System.out.println(part1(fileContent));

        // Part 2 of the Challenge.
        System.out.println(part2());
    }

    public static int part1(List<String> lines)
    {
        for (String line : lines)
        {
            if (line.isBlank())
            {
                continue;
            }

            line = line.strip();

            if (!line.contains("="))
            {
                instructions = line.toCharArray();
                continue;
            }

            String rootNode = line.substring(0, 3);
            String goTo = line.substring(6).replaceAll("[(),]", "");

            documents.put(rootNode, goTo);
        }

        int steps = 0;

        String nextNode = "AAA";

        for (int i = 0; i < instructions.length; i++)
        {
            steps++;

            char instruction = instructions[i];

            String goTo = documents.get(nextNode);

            String[] nextValues = goTo.split(" ");
            String left = nextValues[0];
            String right = nextValues[1];

            if (instruction == 'L')
            {
                nextNode = left;
            }

            if (instruction == 'R')
            {
                nextNode = right;
            }

            if (nextNode.equals("ZZZ"))
            {
                break;
            }

            if (i == (instructions.length - 1))
            {
                i = -1;
            }
        }
        return steps;
    }

    public static long part2()
    {
        List<String> aNodes = documents.keySet().stream().filter(rootNode -> rootNode.endsWith("A")).toList();
        ArrayList<Integer> steps = new ArrayList<>();

        for (String aNode : aNodes)
        {
            int aSteps = 0;

            String nextNode = aNode;

            for (int i = 0; i < instructions.length; i++)
            {
                aSteps++;

                char instruction = instructions[i];

                String goTo = documents.get(nextNode);

                String[] nextValues = goTo.split(" ");
                String left = nextValues[0];
                String right = nextValues[1];

                if (instruction == 'L')
                {
                    nextNode = left;
                }

                if (instruction == 'R')
                {
                    nextNode = right;
                }

                if (nextNode.endsWith("Z"))
                {
                    break;
                }

                if (i == (instructions.length - 1))
                {
                    i = -1;
                }
            }
            steps.add(aSteps);
        }
        return lcm(steps);
    }

    public static long gcd(long num1, long num2)
    {
        if (num2 == 0)
        {
            return num1;
        }
        return gcd(num2, num1 % num2);
    }

    public static long lcm(List<Integer> steps)
    {
        long lcm = steps.get(0);

        for (int i = 1; i < steps.size(); i++)
        {
            long num1 = lcm;
            long num2 = steps.get(i);

            long gcd = gcd(num1, num2);

            lcm = (num1 * num2) / gcd;
        }
        return lcm;
    }
}
