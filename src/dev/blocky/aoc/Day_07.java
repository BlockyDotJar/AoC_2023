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
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Day_07
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/rsc/Day_07.txt");
        List<String> fileContent = Files.readAllLines(file.toPath(), UTF_8);

        // Part 1 of the Challenge.
        System.out.println(part1And2(fileContent, false));

        // Part 2 of the Challenge.
        System.out.println(part1And2(fileContent, true));
    }

    public static int part1And2(List<String> lines, boolean joker)
    {
        ArrayList<Hand> hands = new ArrayList<>();

        for (String line : lines)
        {
            if (line.isBlank())
            {
                continue;
            }

            String[] parts = line.split(" ");

            String hand = parts[0];
            int bid = Integer.parseInt(parts[1]);

            Hand cardHand = new Hand(hand, bid);
            hands.add(cardHand);
        }

        hands.sort((firstHand, secondHand) ->
        {
            if (firstHand.getCardType(joker) == secondHand.getCardType(joker))
            {
                return firstHand.getNonNumericHand(joker).compareTo(secondHand.getNonNumericHand(joker));
            }
            return Integer.compare(firstHand.getCardType(joker), secondHand.getCardType(joker));
        });

        return hands.stream().mapToInt(hand -> hand.bid * (hands.indexOf(hand) + 1)).sum();
    }

    record Hand(String hand, int bid)
    {
        private static HashMap<Character, Character> getStrengths(boolean joker)
        {
            HashMap<Character, Character> strengthChars = new HashMap<>();

            List<Character> strengths = joker ? Arrays.asList('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
                    : Arrays.asList('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');

            Collections.reverse(strengths);

            for (int i = 0; i < strengths.size(); i++)
            {
                strengthChars.put(strengths.get(i), (char) (i + 65));
            }
            return strengthChars;
        }

        public String getNonNumericHand(boolean joker)
        {
            StringBuilder nonNumericHand = new StringBuilder();

            for (char card : hand.toCharArray())
            {
                nonNumericHand.append(getStrengths(joker).get(card));
            }
            return nonNumericHand.toString();
        }

        public int getCardType(boolean joker)
        {
            HashMap<Character, Integer> cards = new HashMap<>();

            int cardType = 0, division = 10000;

            if (!joker)
            {
                for (char card : hand.toCharArray())
                {
                    cards.put(card, cards.getOrDefault(card, 0) + 1);
                }

                List<Integer> reversedCards = cards.values().stream().sorted(Collections.reverseOrder()).toList();

                for (int reversedCard : reversedCards)
                {
                    cardType += reversedCard * division;
                    division /= 10;
                }
                return cardType;
            }

            int jokerCards = 0;

            for (char card : hand.toCharArray())
            {
                if (card == 'J')
                {
                    jokerCards++;
                    continue;
                }
                cards.put(card, cards.getOrDefault(card, 0) + 1);
            }

            ArrayList<Integer> reversedCards = cards.values().stream()
                    .sorted(Collections.reverseOrder())
                    .collect(Collectors.toCollection(ArrayList::new));

            if (!reversedCards.isEmpty())
            {
                reversedCards.set(0, reversedCards.get(0) + jokerCards);
            }

            if (reversedCards.isEmpty())
            {
                reversedCards.add(jokerCards);
            }

            for (int reversedCard : reversedCards)
            {
                cardType += reversedCard * division;
                division /= 10;
            }
            return cardType;
        }
    }
}
