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
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Day_19
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_19.txt");
        List<String> fileContent = Files.readAllLines(file.toPath(), UTF_8);

        // Part 1 of the Challenge.
        System.out.println(part1(fileContent));

        // Part 2 of the Challenge.
        System.out.println(part2(fileContent));
    }

    public static int part1(List<String> lines)
    {
        ArrayList<Workflow> workflows = new ArrayList<>();
        ArrayList<Rating> ratings = new ArrayList<>();

        Workflow in = null;

        for (String line : lines)
        {
            if (line.isBlank())
            {
                continue;
            }

            if (line.matches("^\\{(.*)}$"))
            {
                String[] parts = line.substring(1, line.length() - 1).split(",");
                int x = Integer.parseInt(parts[0].substring(2));
                int m = Integer.parseInt(parts[1].substring(2));
                int a = Integer.parseInt(parts[2].substring(2));
                int s = Integer.parseInt(parts[3].substring(2));

                Rating rating = new Rating(x, m, a, s);
                ratings.add(rating);
                continue;
            }

            String name = line.substring(0, line.indexOf("{"));

            String[] rulesRaw = line.substring(line.indexOf("{") + 1, line.length() - 1).split(",");
            List<String> rules = Arrays.stream(rulesRaw).filter(rule -> rule.contains(":")).toList();

            String fallback = line.substring(line.lastIndexOf(",") + 1, line.indexOf("}"));

            Workflow workflow = new Workflow(name, rules, fallback);

            if (name.equals("in"))
            {
                in = workflow;
                continue;
            }

            workflows.add(workflow);
        }

        int sum = 0;

        for (Rating rating : ratings)
        {
            sum += getAcceptedValues(in, rating, workflows);
        }
        return sum;
    }

    @Unsolved(willSolveInFuture = false)
    public static int part2(@SuppressWarnings("unused") List<String> lines)
    {
        return -1;
    }

    private static int getAcceptedValues(Workflow in, Rating rating, List<Workflow> workflows)
    {
        int x = rating.x();
        int m = rating.m();
        int a = rating.a();
        int s = rating.s();

        List<String> inRules = in.rules();
        String inFallback = in.fallback();

        String next = null;

        for (String rule : inRules)
        {
            char neededValue = rule.charAt(0);
            char comparisonMark = rule.charAt(1);

            String[] parts = rule.split(":");
            int valueToCompare = Integer.parseInt(parts[0].substring(2));
            String nextWorkflow = parts[1];

            int neededInt = switch (neededValue)
            {
                case 'x' -> x;
                case 'm' -> m;
                case 'a' -> a;
                case 's' -> s;
                default -> 0;
            };

            if (comparisonMark == '>')
            {
                if (neededInt < valueToCompare)
                {
                    continue;
                }
                next = nextWorkflow;
                break;
            }

            if (comparisonMark == '<')
            {
                if (neededInt > valueToCompare)
                {
                    continue;
                }
                next = nextWorkflow;
                break;
            }
        }

        if (next == null)
        {
            next = inFallback;
        }

        if (next.equals("A"))
        {
            return x + m + a + s;
        }

        if (next.equals("R"))
        {
            return 0;
        }

        Workflow nextWorkflow = findNextWorkflow(workflows, next);
        return getAcceptedValues(nextWorkflow, rating, workflows);
    }

    private static Workflow findNextWorkflow(List<Workflow> workflows, String next)
    {
        for (Workflow workflow : workflows)
        {
            if (workflow.name().equals(next))
            {
                return workflow;
            }
        }
        return null;
    }

    record Workflow(String name, List<String> rules, String fallback)
    {
    }

    record Rating(int x, int m, int a, int s)
    {
    }
}
