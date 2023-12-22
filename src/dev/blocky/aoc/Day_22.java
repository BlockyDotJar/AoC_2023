package dev.blocky.aoc;

import dev.blocky.aoc.annotations.Unsolved;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Unsolved(willSolveInFuture = false)
public class Day_22
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_22.txt");
        List<String> fileContent = Files.readAllLines(file.toPath(), UTF_8);

        // Part 1 of the Challenge.
        System.out.println(part1(fileContent));

        // Part 2 of the Challenge.
        System.out.println(part2(fileContent));
    }

    public static int part1(@SuppressWarnings("unused") List<String> lines)
    {
        return -1;
    }

    public static long part2(@SuppressWarnings("unused") List<String> lines)
    {
        return -1;
    }
}
