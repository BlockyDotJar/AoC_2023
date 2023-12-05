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
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Day_05
{
    private static List<Long> seeds = new ArrayList<>();
    private static final List<AlmanacMap> maps = new ArrayList<>(7);
    private static final List<AlmanacRange> seedToLocationRanges = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_05.txt");
        List<String> fileContent = Files.readAllLines(file.toPath(), UTF_8);

        // Part 1 of the Challenge.
        System.out.println(part1(fileContent));

        // Part 2 of the Challenge.
        System.out.println(part2());
    }

    public static long part1(List<String> lines)
    {
        AlmanacMap almanacMap = new AlmanacMap();

        for (String line : lines)
        {
            if (line.isBlank())
            {
                continue;
            }

            line = line.strip();

            if (!line.matches("^\\d+(.*)") && !line.contains("map"))
            {
                String[] seedsRaw = line.substring(7).strip().split(" ");
                seeds = Arrays.stream(seedsRaw).mapToLong(Long::parseLong).boxed().toList();
                continue;
            }

            if (line.contains("map"))
            {
                almanacMap = new AlmanacMap();
                maps.add(almanacMap);
                continue;
            }

            String[] parts = line.split(" ");

            List<Long> numbers = Arrays.stream(parts).mapToLong(Long::parseLong).boxed().toList();
            long destinationRangeStart = numbers.get(0);
            long sourceRangeStart = numbers.get(1);
            long rangeLength = numbers.get(2);

            almanacMap.setRange(destinationRangeStart, sourceRangeStart, rangeLength);
        }

        List<Long> locations = seeds.stream().mapToLong(seed ->
                {
                    long currentSeed = seed;

                    for (AlmanacMap map : maps)
                    {
                        currentSeed = map.getLocationSeed(currentSeed);
                    }

                    return currentSeed;
                }
        ).boxed().toList();

        return Collections.min(locations);
    }

    public static long part2()
    {
        for (int i = 0; i < seeds.size(); i += 2)
        {
            long rangeStart = seeds.get(i);
            long rangeLength = seeds.get(i + 1);

            setupRanges(0, rangeStart, rangeLength, rangeStart);
        }

        List<Long> locations = seedToLocationRanges.stream().mapToLong(range -> range.destinationRangeStart).boxed().toList();

        return Collections.min(locations);
    }

    private static void setupRanges(int map, long rangeStart, long rangeLength, long sourceRangeStart)
    {
        List<AlmanacRange> ranges = maps.get(map).getRanges(rangeStart, rangeLength);

        if (map == (maps.size() - 1))
        {
            for (AlmanacRange range : ranges)
            {
                AlmanacRange almanacRange = new AlmanacRange(range.destinationRangeStart, sourceRangeStart, rangeLength);
                seedToLocationRanges.add(almanacRange);

                sourceRangeStart += range.rangeLength;
            }
            return;
        }

        for (AlmanacRange range : ranges)
        {
            setupRanges(map + 1, range.destinationRangeStart, range.rangeLength, sourceRangeStart);
            sourceRangeStart += range.rangeLength;
        }
    }

    record AlmanacRange(long destinationRangeStart, long sourceRangeStart, long rangeLength)
    {
    }

    static class AlmanacMap
    {
        private final ArrayList<AlmanacRange> ranges = new ArrayList<>();

        public void setRange(long destinationRangeStart, long sourceRangeStart, long rangeLength)
        {
            AlmanacRange almanacRange = new AlmanacRange(destinationRangeStart, sourceRangeStart, rangeLength);
            this.ranges.add(almanacRange);
            ranges.sort(Comparator.comparing(range -> range.sourceRangeStart));
        }

        public long getLocationSeed(long seed)
        {
            for (AlmanacRange range : ranges)
            {
                if (seed >= range.sourceRangeStart && seed <= (range.sourceRangeStart + range.rangeLength))
                {
                    return range.destinationRangeStart + (seed - range.sourceRangeStart);
                }
            }
            return seed;
        }

        public List<AlmanacRange> getRanges(long rangeStart, long rangeLength)
        {
            List<AlmanacRange> rangeList = new ArrayList<>();

            for (int i = 0; i < ranges.size() && rangeLength > 0; i++)
            {
                AlmanacRange range = ranges.get(i);

                if (rangeStart > (range.sourceRangeStart + range.rangeLength))
                {
                    continue;
                }

                if (rangeStart < range.sourceRangeStart)
                {
                    if ((rangeStart + rangeLength) < range.sourceRangeStart)
                    {
                        rangeList.add(new AlmanacRange(rangeStart, rangeStart, rangeLength));
                        rangeLength = 0;
                        continue;
                    }

                    AlmanacRange almanacRange = new AlmanacRange(rangeStart, rangeStart, range.sourceRangeStart - rangeStart);

                    if ((rangeStart + rangeLength) < (range.sourceRangeStart + range.rangeLength))
                    {
                        AlmanacRange ar = new AlmanacRange(range.destinationRangeStart, range.sourceRangeStart, (rangeStart + rangeLength) - range.sourceRangeStart);

                        rangeList.add(almanacRange);
                        rangeList.add(ar);

                        rangeLength = 0;
                        continue;
                    }

                    AlmanacRange ar = new AlmanacRange(range.destinationRangeStart, range.sourceRangeStart, range.rangeLength);

                    rangeList.add(almanacRange);
                    rangeList.add(ar);

                    long currentRangeLength = range.sourceRangeStart + range.rangeLength - rangeStart;

                    rangeLength -= currentRangeLength;
                    rangeStart += currentRangeLength;
                    continue;
                }

                long start = rangeStart - range.sourceRangeStart;

                if ((rangeStart + rangeLength) < (range.sourceRangeStart + range.rangeLength))
                {
                    AlmanacRange almanacRange = new AlmanacRange(range.destinationRangeStart + start, range.sourceRangeStart + start, rangeLength);
                    rangeList.add(almanacRange);

                    rangeLength = 0;
                    continue;
                }

                AlmanacRange almanacRange = new AlmanacRange(range.destinationRangeStart + start, range.sourceRangeStart + start, range.rangeLength - start);
                rangeList.add(almanacRange);

                long currentRangeLength = (range.rangeLength - start);

                rangeLength -= currentRangeLength;
                rangeStart += currentRangeLength;
            }

            if (rangeLength > 0)
            {
                AlmanacRange range = new AlmanacRange(rangeStart, rangeStart, rangeLength);
                rangeList.add(range);
            }
            return rangeList;
        }
    }
}
