package com.himself12794.heroesmod.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;

public final class RandomUtils {

	private RandomUtils() {
	}

	/**
	 * Selects a random weighted item out of the collection. Returns null if
	 * collection is empty, or all items have a weight of less than 0.
	 * @param items
	 * 
	 * @return
	 */
	public static <T extends IWeightedItem> T selectRandomWeightedItem(Collection<T> items) {
		return RandomUtils.selectRandomWeightedItem(null, items);
	}

	/**
	 * Selects a random weighted item out of the collection. Returns null is
	 * collection is empty, or all items have a weight of null.
	 * @param rand the random object to use
	 * @param items
	 * 
	 * @return
	 */
	public static <T extends IWeightedItem> T selectRandomWeightedItem(Random rand, Collection<T> items) {

		if (rand == null)
			rand = new Random();

		float totalWeight = 0.0F;
		Map<Range, IWeightedItem> ranges = Maps.newHashMap();

		for (IWeightedItem item : items) {
			
			if (item.getWeight() > 0.0F) {
				ranges.put(
						Range.closedOpen(totalWeight,
								totalWeight + item.getWeight()), item);
				totalWeight += item.getWeight();
			}
			
		}

		float choice = rand.nextFloat() * totalWeight;

		for (Entry<Range, IWeightedItem> entry : ranges.entrySet()) {

			System.out.println(entry.getKey());
			if (entry.getKey().contains(choice)) {
				return (T) entry.getValue();
			}

		}

		return null;

	}

}
