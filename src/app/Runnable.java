package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import distrgenalg.DistrGenAlg;
import packing.BppProblem;
import packing.InitialSolution;

public class Runnable {

	public static void main(String[] args) {
		String[] filenames = { "EasyA.txt", "MediumA.txt", "HardA.txt" };
		long seed = System.currentTimeMillis();

		Scanner sc = new Scanner(System.in);

		System.out.println("Constructive Hyper-Heuristics using EvoHyp for the One-Dimensional Bin-Packing Problem");
		System.out.println(
				"Do you wish to enter a Seed? (Number Only) (Default is System Current Time in Milliseconds)\n1 - Yes\n2 - No");
		int choice = sc.nextInt();

		while (choice < 1 || choice > 2) {
			System.out.println(
					"\nUnavailable Option\nDo you wish to enter a Seed? (Number Only) (Default is System Current Time in Milliseconds)\n1 - Yes\n2 - No");
			choice = sc.nextInt();
		}

		if (choice == 1) {
			System.out.println("\nEnter Seed");
			seed = sc.nextLong();
		}

		System.out.println("Choose a Problem Instance (Number Only)");

		for (int i = 0; i < filenames.length; i++)
			System.out.println((i + 1) + " - " + filenames[i]);

		int num = sc.nextInt();

		while (num < 0 || num > filenames.length) {
			System.out.println();
			System.out.println("Option Not Available");
			System.out.println("Choose a Problem Instance (Number Only)");

			for (int i = 0; i < filenames.length; i++)
				System.out.println((i + 1) + " - " + filenames[i]);
			System.out.println();

			num = sc.nextInt();
		}

		String file = "resources\\" + filenames[num - 1];

		BppProblem bpp = new BppProblem();
		bpp.setFilename(file);
		String heuristics = "FBNW";
		int cores = 4;

		DistrGenAlg dga = new DistrGenAlg(seed, heuristics, cores);
		dga.setParameters("Parameters.txt");
		dga.setProblem(bpp);
		dga.setAllowDuplicates(false);

		long start = System.currentTimeMillis();
		InitialSolution solution = (InitialSolution) dga.evolve();
		long end = System.currentTimeMillis();

		System.out.println("\nBest Solution");
		System.out.println("--------------");
		System.out.println("Seed: " + seed);
		System.out.println("Fitness: " + solution.getFitness());
		System.out.println("Time Taken: " + (end - start) + "ms");
		System.out.println("Heuristic combination: " + solution.getHeuCom());
		System.out.println("Solution: ");
		solution.print();
		System.out.println("Objective Value: " + solution.getObjectiveValue());

		BufferedWriter bw = null;
		FileWriter fw = null;
		String FILENAME = "Outputs\\output";
		int t = 1;
		try {

			File f = new File(FILENAME + t + ".txt");

			// if file doesn't exists, then create it

			while (f.exists()) {
				t++;
				f = new File(FILENAME + t + ".txt");
			}
			f.createNewFile();

			// true = append file
			fw = new FileWriter(f.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write("Problem Instance: " + filenames[num - 1]);
			bw.newLine();
			bw.write("Best Solution");
			bw.newLine();
			bw.write("--------------");
			bw.newLine();
			bw.write("Seed: " + seed);
			bw.newLine();
			bw.write("Fitness: " + solution.getFitness());
			bw.newLine();
			bw.write("Time Taken: " + (end - start) + " ms");
			bw.newLine();
			bw.write("Heuristic combination: " + solution.getHeuCom());
			bw.newLine();
			bw.write("Objective Value: " + solution.getObjectiveValue());

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

		System.out.println("\nPush Enter to Continue");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sc.close();
	}

}
