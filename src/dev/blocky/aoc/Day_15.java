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

import static java.nio.charset.StandardCharsets.UTF_8;

public class Day_15
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_15.txt");
        String fileContent = Files.readString(file.toPath(), UTF_8);

        // Part 1 of the Challenge.
        System.out.println(part1(fileContent));

        // Part 2 of the Challenge.
        System.out.println(part2(fileContent));
    }

    public static int part1(String fileContent)
    {
        String[] initializationSequences = fileContent.strip().split(",");

        int sum = 0;

        for (String hashStr : initializationSequences)
        {
            sum += getCurrentValue(hashStr);
        }

        return sum;
    }

    public static long part2(String fileContent)
    {
        String[] initializationSequences = fileContent.strip().split(",");

        Box[] boxes = new Box[256];
        fill(boxes);

        for (String hashStr : initializationSequences)
        {
            String[] parts = hashStr.split("[=-]");
            String label = parts[0];
            int focalLength = parts.length == 2 ? Integer.parseInt(parts[1]) : -1;

            int currentValue = getCurrentValue(label);
            Box box = boxes[currentValue];

            List<Lens> lenses = box.lenses();
            Lens lens = lenses.stream().filter(lns -> lns.label().equals(label)).findAny().orElse(null);

            if (lens != null)
            {
                if (hashStr.contains("-"))
                {
                    lenses.remove(lens);
                    continue;
                }

                Lens lns = new Lens(label, focalLength);
                lenses.set(lenses.indexOf(lens), lns);
                continue;
            }

            if (hashStr.contains("="))
            {
                Lens lns = new Lens(label, focalLength);
                lenses.add(lns);
            }
        }

        int sum = 0;

        for (int i = 0; i < boxes.length; i++)
        {
            int focusingPower = 0;

            Box box = boxes[i];
            List<Lens> lenses = box.lenses();

            for (int j = 0; j < lenses.size(); j++)
            {
                Lens lens = lenses.get(j);
                focusingPower += (i + 1) * (j + 1) * lens.focalLength();
            }

            sum += focusingPower;
        }

        return sum;
    }

    private static int getCurrentValue(String hashStr)
    {
        int currentValue = 0;
        char[] hashChars = hashStr.toCharArray();

        for (char hashChar : hashChars)
        {
            currentValue += hashChar;
            currentValue = (currentValue * 17) % 256;
        }

        return currentValue;
    }

    private static void fill(Box[] boxes)
    {
        for (int i = 0; i < boxes.length; i++)
        {
            ArrayList<Lens> emptyLenses = new ArrayList<>();
            Box box = new Box(emptyLenses);

            boxes[i] = box;
        }
    }

    record Lens(String label, int focalLength)
    {
    }

    record Box(List<Lens> lenses)
    {
    }
}
