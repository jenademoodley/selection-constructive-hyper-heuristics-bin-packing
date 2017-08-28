package packing;

import java.util.ArrayList;

import bpp2.BPP2;

public class BinPacking {
	private ArrayList<ArrayList<Integer>> bins;
	private double fitness;
	private String filename;

	public BinPacking() {

	}

	public BinPacking(String filename) {
		this.filename = filename;
		bins = new ArrayList<>();
		bins.add(new ArrayList<Integer>());
	}

	public ArrayList<ArrayList<Integer>> getSolution() {
		return bins;
	}

	public void createSolution(String heuristics) {
		BPP2 bpp = new BPP2(filename);
		bpp.readFile();
		int[] items = bpp.getItemWeights();
		int capacity = bpp.getBinCap();

		int c = 0;
		for (int i = 0; i < items.length; i++) {
			char heuristic = heuristics.charAt(c);
			switch (heuristic) {
			case 'F':
				firstFit(items[i], capacity);
				break;

			case 'N':
				nextFit(items[i], capacity);
				break;

			case 'B':
				bestFit(items[i], capacity);
				break;

			case 'W':
				worstFit(items[i], capacity);
				break;

			default:
				firstFit(items[i], capacity);
				break;
			}

			c++;
			if (c == heuristics.length())
				c = 0;
		}
	}

	public void firstFit(int item, int capacity) {
		boolean addNewBin = true;

		for (int j = 0; j < bins.size(); j++) {
			int outcome = arraySum(bins.get(j)) + item;
			if (outcome <= capacity) {
				bins.get(j).add(item);
				addNewBin = false;
				break;
			}
		}

		if (addNewBin) {
			ArrayList<Integer> newBin = new ArrayList<>();
			newBin.add(item);
			bins.add(newBin);
		}

	}

	public void nextFit(int item, int capacity) {
		int counter = bins.size() - 1;

		int outcome = arraySum(bins.get(counter)) + item;
		if (outcome <= capacity)
			bins.get(counter).add(item);

		else {
			ArrayList<Integer> newBin = new ArrayList<>();
			newBin.add(item);
			bins.add(newBin);
		}
	}

	public void bestFit(int item, int capacity) {
		boolean addNewBin = true;

		int best = capacity;
		int counter = -1;

		for (int j = 0; j < bins.size(); j++) {
			int outcome = arraySum(bins.get(j)) + item;
			int space = capacity - outcome;
			if (space >= 0 && space < best) {
				best = space;
				counter = j;
				addNewBin = false;
			}
		}

		if (addNewBin) {
			ArrayList<Integer> newBin = new ArrayList<>();
			newBin.add(item);
			bins.add(newBin);
		}

		else
			bins.get(counter).add(item);
	}

	public void worstFit(int item, int capacity) {
		boolean addNewBin = true;

		int best = 0;
		int counter = -1;

		for (int j = 0; j < bins.size(); j++) {
			int outcome = arraySum(bins.get(j)) + item;
			int space = capacity - outcome;
			if (space <= capacity && space >= best) {
				best = space;
				counter = j;
				addNewBin = false;
			}
		}

		if (addNewBin) {
			ArrayList<Integer> newBin = new ArrayList<>();
			newBin.add(item);
			bins.add(newBin);
		}

		else
			bins.get(counter).add(item);

	}

	public String toString() {
		String temp = "";
		for (int i = 0; i < bins.size(); i++) {
			temp += ("Bin " + (i + 1) + ":\t\t");
			for (int j = 0; j < bins.get(i).size(); j++) {
				temp += ("\t" + bins.get(i).get(j));
			}
			temp += "\n";
		}
		return temp;
	}

	public void displayBins(ArrayList<ArrayList<Integer>> bins) {
		for (int i = 0; i < bins.size(); i++) {
			System.out.print("Bin " + (i + 1) + ":\t\t");
			for (int j = 0; j < bins.get(i).size(); j++) {
				System.out.print("\t" + bins.get(i).get(j));
			}
			System.out.println();
		}
	}

	public double getFitness() {
		BPP2 bpp = new BPP2(filename);
		bpp.readFile();
		int capacity = bpp.getBinCap();
		fitness = calculateFitness(bins, capacity);
		return fitness;
	}

	private static double calculateFitness(ArrayList<ArrayList<Integer>> bins, int cap) {
		double fitness = 0;
		double sum = 0;

		for (int i = 0; i < bins.size(); i++) {
			double fullness = 0;
			for (int j = 0; j < bins.get(i).size(); j++) {
				fullness += bins.get(i).get(j);
			}

			sum += Math.pow((fullness / cap), 2);
		}

		fitness = 1 - (sum / bins.size());
		double fit = Math.round(fitness * 10000000);
		fit = fit / 10;
		fitness = Math.round(fit);
		fitness = fitness / 1000000;

		return fitness;
	}

	public static int arraySum(ArrayList<Integer> array) {
		int sum = 0;
		for (int i = 0; i < array.size(); i++)
			sum += array.get(i);

		return sum;
	}

}
