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

public class Day_18
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_18.txt");
        List<String> fileContent = Files.readAllLines(file.toPath(), UTF_8);

        // Part 1 of the Challenge.
        System.out.println(part1(fileContent));

        // Part 2 of the Challenge.
        System.out.println(part2(fileContent));
    }

    public static long part1(List<String> lines)
    {
        ArrayList<DigInstruction> digInstructions = new ArrayList<>();

        for (String line : lines)
        {
            if (line.isBlank())
            {
                continue;
            }

            String[] parts = line.split(" ");

            Direction direction = Direction.getDirection(parts[0]);
            int meters = Integer.parseInt(parts[1]);

            DigInstruction digInstruction = new DigInstruction(direction, meters);

            digInstructions.add(digInstruction);
        }

        int sum = digInstructions.stream().mapToInt(DigInstruction::meters).sum();

        return getArea(digInstructions, sum);
    }

    public static long part2(List<String> lines)
    {
        ArrayList<DigInstruction> digInstructions = new ArrayList<>();

        for (String line : lines)
        {
            if (line.isBlank())
            {
                continue;
            }

            String[] parts = line.split(" ");

            String hexCodeRaw = parts[2].substring(1, parts[2].length() - 1);
            String realHexCode = hexCodeRaw.substring(1, hexCodeRaw.length() - 1);

            int lastHexNumber = Integer.parseInt(hexCodeRaw.substring(hexCodeRaw.length() - 1));

            Direction direction = Direction.getDirection(lastHexNumber);
            int meters = Integer.parseInt(realHexCode, 16);

            DigInstruction digInstruction = new DigInstruction(direction, meters);

            digInstructions.add(digInstruction);
        }

        int sum = digInstructions.stream().mapToInt(DigInstruction::meters).sum();

        return getArea(digInstructions, sum);
    }

    private static long getArea(List<DigInstruction> digInstructions, int sum)
    {
        return (getShoelace(digInstructions) / 2) + 1 + (sum / 2);
    }

    private static long getShoelace(List<DigInstruction> digInstructions)
    {
        long shoelace = 0;

        long x = 0, y = 0;
        long lastX = 0, lastY = 0;

        for (DigInstruction digInstruction : digInstructions)
        {
            Direction direction = digInstruction.direction();
            int meters = digInstruction.meters();

            switch (direction)
            {
                case UP -> y -= meters;
                case DOWN -> y += meters;
                case LEFT -> x -= meters;
                case RIGHT -> x += meters;
            }

            shoelace += (lastX * y) - (lastY * x);

            lastX = x;
            lastY = y;
        }
        return shoelace;
    }

    record DigInstruction(Direction direction, int meters)
    {
    }

    enum Direction
    {
        UP("U", 3), DOWN("D", 1), LEFT("L", 2), RIGHT("R", 0);

        private final String direction;
        private final int directionNumber;

        Direction(String direction, int directionNumber)
        {
            this.direction = direction;
            this.directionNumber = directionNumber;
        }

        public static Direction getDirection(String direction)
        {
            for (Direction dir : values())
            {
                if (dir.direction.equals(direction))
                {
                    return dir;
                }
            }
            return null;
        }

        public static Direction getDirection(int directionNumber)
        {
            for (Direction dir : values())
            {
                if (dir.directionNumber == directionNumber)
                {
                    return dir;
                }
            }
            return null;
        }
    }
}
